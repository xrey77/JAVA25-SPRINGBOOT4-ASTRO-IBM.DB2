import { useState} from "react";
import axios from "axios";
import $ from 'jquery';

const api = axios.create({
  baseURL: "http://localhost:9090",
  headers: {'Accept': 'application/json',
            'Content-Type': 'application/json'}
});

function MfaForm() {
    const [otp, setOtp] = useState<string>('');
    const [message, setMessage] = useState<string>('');
    const [userid, setUserid] = useState<string>('');
    const [token, setToken] = useState<string>('');
    const [disable, setDisable] = useState<boolean>(false);

    const submitOtp = (event: any) => {
        event.preventDefault();
        const userId = sessionStorage.getItem('USERID');
        if (userId) setUserid(userId);
        const userToken = sessionStorage.getItem('TOKEN');
        if (userToken) setToken(userToken);
  
        const jsondata = JSON.stringify({ otp: otp });
        api.patch(`api/mfa/verifytotp/${userid}`, jsondata,  { headers: {
            Authorization: `Bearer ${token}`
        }})
        .then((res: any) => {
                  setMessage(res.data.message);
                    sessionStorage.setItem('USERNAME',res.data.username);
                    setTimeout(() => {
                        $("#staticMfa").hide();
                        setMessage('');
                        window.location.reload();
                    }, 3000);
          }, (error: any) => {
                if (error.response) {
                  setMessage(error.response.data.message);
                } else {
                  setMessage(error.message);
                }
                setTimeout(() => {
                    setMessage('');
                    setDisable(false);
                }, 3000);
                return;
        });                
    }

    const closeMfaDialog = (event: any) => {
        event.preventDefault();
        setOtp('');
        setMessage('');
        window.sessionStorage.removeItem('USERID');
        window.sessionStorage.removeItem('USERNAME');
        window.sessionStorage.removeItem('USERPIC');
        window.sessionStorage.removeItem('TOKEN');
        location.href="/";
        location.reload();
    }

  return (
<div className="modal fade" id="staticMfa" data-bs-backdrop="static" data-bs-keyboard="false" tabIndex={-1} aria-labelledby="staticMfaLabel" aria-hidden="true">
  <div className="modal-dialog modal-sm modal-dialog-centered">
    <div className="modal-content">
      <div className="modal-header bg-warning">
          <h5 className="modal-title text-primay fs-5" id="staticMfaLabel">Multi-Factor Authentictor</h5>
          <button onClick={closeMfaDialog} type="button" className="btn-close btn-close-primary" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div className="modal-body">
      <form onSubmit={submitOtp} autoComplete="off">
              <div className="mb-3">
                <input type="text" required  value={otp} onChange={e => setOtp(e.target.value)} disabled={disable} className="form-control border-warning" id="otp" placeholder="enter 6-digit OTP code"/>
              </div>
              <button type="submit" disabled={disable} className="btn btn-warning">submit</button>
          </form>


      </div>
      <div className="modal-footer">
          <div className="w-100 text-center text-danger">{message}</div>
      </div>
    </div>
  </div>
</div>
  );
}

export default MfaForm;