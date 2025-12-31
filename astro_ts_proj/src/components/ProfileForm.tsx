import { useState, useEffect } from "react";
import axios from "axios";
import jQuery from 'jquery';

const mfaapi = axios.create({
  baseURL: "http://localhost:9090",
  headers: {'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'inherit'},
})

const api = axios.create({
  baseURL: "http://localhost:9090",
  headers: {'Accept': 'application/json',
          'Content-Type': 'multipart/form-data',
          'Authorization': 'inherit'},
})

export default function Profile() {    
    const [userid, setUserid] = useState<string>('');;
    const [lname, setLname] = useState<string>('');
    const [fname, setFname] = useState<string>('');
    const [email, setEmail] = useState<string>('');
    const [mobile, setMobile] = useState<string>('');
    const [userpicture, setUserpicture] = useState<string>('');
    const [token, setToken] = useState<string>('');
    const [newpassword, setNewPassword ] = useState<string>('');
    const [confnewpassword, setConfNewPassword ] = useState<string>('');    
    const [profileMsg, setProfileMsg] = useState<string>('');
    const [showmfa, setShowMfa] = useState<boolean>(false);
    const [showpwd, setShowPwd] = useState<boolean>(false);
    const [showupdate, setShowUpdate] = useState<boolean>(false);
    const [qrcodeurl, setQrcodeurl] = useState<string>('');

    const fetchUserData = (id: any, token: any) => {
        api.get(`/api/getuserid/${id}`,{headers: {
            Authorization: `Bearer ${token}`
        }})
        .then((res: any) => {
            setLname(res.data.lastname); 
            setFname(res.data.firstname); 
            setEmail(res.data.email);
            setMobile(res.data.mobile);
            if (res.data.qrcodeurl !== null) {
                setQrcodeurl(res.data.qrcodeurl)
            } else {
                setQrcodeurl(`http://localhost:9090/images/qrcode.png`)
            }
            setUserpicture(`http://localhost:9090/users/${res.data.userpic}`);
        }, (error: any) => {
            if (error.response) {
                setProfileMsg(error.response.data.message);
            } else {
                setProfileMsg(error.message);
            }
            setTimeout(() => {
                setProfileMsg('');
            },3000);
            return;
        });
            
    };    

    useEffect(() => {
        jQuery("#password").prop('disabled', true);
        if (userpicture === null) {
            // $('#userpic').attr('src', '/pix.png');
            setUserpicture('http://localhost:9090/images/pix.png')
        }

        let userId: any = sessionStorage.getItem('USERID');
        if (userId !== null) {
            setUserid(userId)
        } else {
            setUserid('')
        }
        let xtoken: any = sessionStorage.getItem('TOKEN');
        if (xtoken !== null) {
            setToken(xtoken);
        } else {
            setToken('');
        }
        fetchUserData(userId, xtoken);
    },[userid, token, userpicture]) 

    const submitProfile = (e: any) => {
        e.preventDefault();
        const data =JSON.stringify({ id: userid, lastname: lname, firstname: fname, mobile: mobile });
        mfaapi.patch(`/api/updateprofile/${userid}`, data, { headers: {
            Authorization: `Bearer ${token}`
        }})
        .then((res: any) => {
            setProfileMsg(res.data.message);
            setTimeout(() => {
                setProfileMsg('');
            },3000);
        }, (error: any) => {
            if (error.response) {
                setProfileMsg(error.response.data.message);
            } else {
                setProfileMsg(error.message);
            }
            setTimeout(() => {
                setProfileMsg('');
            },3000);
            return;
        });
    }

    const cpwdCheckbox = (e: any) => {
        if (e.target.checked) {
            setShowUpdate(true);
            setShowPwd(true);
            // $("#checkChangePassword").prop('disabled', false);
            return;
        } else {
            setNewPassword('');
            setConfNewPassword('');
            setShowPwd(false);
            setShowUpdate(false)
        }
    }

    const changePicture = (event: any) => {
        event.preventDefault();
            var pix = URL.createObjectURL(event.target.files[0]);
            jQuery('#userpic').attr('src', pix);
            const formData = new FormData();
            formData.append('userpic', event.target.files[0]);
            api.patch(`/api/uploadpicture/${userid}`, formData, {headers: {
                Authorization: `Bearer ${token}`
            }})
            .then((res: any) => {
                    setProfileMsg(res.data.message);
                    setTimeout(() => {
                        sessionStorage.setItem('USERPIC', `http://localhost:9090/users/${res.data.userpic}`)
                        setProfileMsg('');
                        location.reload();
                    },3000);
            }, (error: any) => {
                if (error.response) {
                    setProfileMsg(error.response.data.message);
                } else {
                    setProfileMsg(error.message);
                }    
                setTimeout(() => {
                    setProfileMsg('');
                },3000);
                return;
            });
    
    }

    const mfaCheckbox = (e: any) => {
        if (e.target.checked) {
            setShowMfa(true);
            setShowUpdate(true)
        } else {
            setShowMfa(false);
            setShowUpdate(false)
        }
    }

    const enableMFA = () => {
        const data =JSON.stringify({ TwoFactorEnabled: true });
        mfaapi.patch(`/api/mfa/activate/${userid}`, data, {headers: {
            Authorization: `Bearer ${token}`
        }})
        .then((res: any) => {
            setProfileMsg(res.data.message);
            setTimeout(() => {
                setProfileMsg('');
                setQrcodeurl(res.data.qrcodeurl);
            },3000);
        }, (error: any) => {
            if (error.response) {
                setProfileMsg(error.response.data.message);
            } else {
                setProfileMsg(error.message);
            }
            setTimeout(() => {
                setProfileMsg('');
            },3000);
            return;
        });
    }

    const disableMFA = () => {
        const data =JSON.stringify({ TwoFactorEnabled: false });      
        mfaapi.patch(`/api/mfa/activate/${userid}`, data, {headers: {
            Authorization: `Bearer ${token}`
        }})
        .then((res: any) => {
            setProfileMsg(res.data.message);
            setTimeout(() => {
                setQrcodeurl('http://localhost:9090/images/qrcode.png');
                setProfileMsg('');
            },3000);            
        }, (error: any) => {
            if (error.response) {
                setProfileMsg(error.response.data.message);
            } else {
                setProfileMsg(error.message);
            }
            setTimeout(() => {
                setProfileMsg('');
            },3000);
            return;
        });
    }

    const changePassword = () => {
        if (newpassword === '') {
            setProfileMsg("Please enter new Pasword.");
            let timer: any = setTimeout(() => {
                setProfileMsg('');
                clearTimeout(timer);
            },3000);
            return;
        }
        if (confnewpassword === '') {
            setProfileMsg("Please enter new Pasword confirmation.");
            let timer: any = setTimeout(() => {
                setProfileMsg('');
                clearTimeout(timer);
            },3000);
            return;            
        }

        if (newpassword !== confnewpassword) {
            setProfileMsg("new Password does not matched.");
            let timer: any = setTimeout(() => {
                setProfileMsg('');
                clearTimeout(timer);
            },3000);
            return;            
        }

        const data =JSON.stringify({ "password": newpassword });      
        mfaapi.patch(`/api/changepassword/${userid}`, data, {headers: {
            Authorization: `Bearer ${token}`
        }})
        .then((res: any) => {
            setProfileMsg(res.data.message);
            setTimeout(() => {
                setProfileMsg('');
            },3000);
        }, (error: any) => {
            if (error.response) {
                setProfileMsg(error.response.data.message);
            } else {
                setProfileMsg(error.message);
            }
            setTimeout(() => {
                setProfileMsg('');
            },3000);
            return;
        });        
    }

    return (
        <div className="container">
        <div className="card  bg-azure border-1">
        <div className="card-header bg-dark border-5 border-white text-white">
            <h3>Profile</h3>
        </div>
        <div className="card-body">
        <form encType="multipart/form-data" autoComplete='false'>
                <div className='row'>
                    <div className='col'>
                        <input className="form-control" id="firstname" name="firstname" type="text" value={fname} onChange={e => setFname(e.target.value)} required  />
                        <input className="form-control mt-2" id="lastname" name="lastname" type="text" value={lname} onChange={e => setLname(e.target.value )} required />
                    </div>
                    <div className='col text-right'>

                    </div>
                </div>
                <div className='row'>
                    <div className='col'>
                        <input className="form-control mt-2" id="email" name="email" type="email" value={email} onChange={e => setEmail(e.target.value)} readOnly placeholder='email address' />
                    </div>
                    <div className='col'>
                        <img id="userpic" src={userpicture} className="userpic" alt="" />
                    </div>
                </div>


                <div className='row'>
                    <div className='col'>
                            <input className="form-control mt-2" id="mobileno" name="mobileno" type="text" value={mobile} onChange={e => setMobile(e.target.value)} required />
                    </div>
                    <div className='col'>
                        <input className="userpicture mt-2 text-white file-input" onChange={changePicture} type="file"/>
                    </div>
                </div>

                <div className='row'>
                    {/* 2-FACTOR AUTHENTICATION */}
                    <div className='col'>
                            <div className="form-check mt-2">
                                <input onChange={mfaCheckbox} className="form-check-input" type="checkbox" value="" id="checkTwoFactor"/>
                                <label className="form-check-label text-white" htmlFor="checkTwoFactor">
                                    Enable 2-Factor Authentication
                                </label>
                            </div>
                            {
                                showmfa === true ? (

                            <div className='row'>
                                <div className='col-5'>
                                      <img src={qrcodeurl} className="qrCode2" alt=""/>
                                </div>
                            <div className='col'>
                                <h5 className='mx-3 text-warning mfa-pos-1'>Requirements</h5>
                                <p className="mx-3 mfa-pos-2 text-white">You need to install <strong>Google or Microsoft Authenticator</strong> in your Mobile Phone, once installed, click Enable Button below, and <strong>SCAN QR CODE</strong>, next time you login, another dialog window will appear, then enter the <strong>OTP CODE</strong> from your Mobile Phone in order for you to login.</p>
                                <div className="mfa-btn">
                                <button onClick={enableMFA} type="button" className='btn btn-primary mfa-btn-1 mx-1'>enable</button>
                                <button onClick={disableMFA} type="button" className='btn btn-secondary mfa-btn-2'>disable</button>
                                </div>
                            </div>
                            </div>
                                )
                                :
                                null
                            }

                    </div>
                    <div className='col'>
                            {/* CHANGE PASSWORD */}
                            <div className="form-check mt-2">
                            <input onChange={cpwdCheckbox} className="form-check-input" type="checkbox" id="checkChangePassword"/>
                            <label className="form-check-label text-white" htmlFor="checkChangePassword">
                                Change Password
                            </label>
                        </div>
                        { showpwd === true ? (
                            <>
                            <input className="form-control mt-2" type="password" id="newPassword" value={newpassword} onChange={e => setNewPassword(e.target.value)} placeholder='enter new Password'/>
                            <input className="form-control mt-1" type="password" id="confNewPassword" value={confnewpassword} onChange={e => setConfNewPassword(e.target.value)} placeholder='confirm new Password'/>
                            <button onClick={changePassword} className='btn btn-primary mt-2' type="button">change password</button>
                            </>
                        )
                        :
                            null
                        }

                    </div>
                </div> 
                {
                    showupdate === false ? (
                        <button onClick={submitProfile} type='submit' className='btn btn-info text-dark mt-2'>update profile</button>
                    )
                    :
                    null
                }
                </form>
        </div>
        <div className="card-footer text-warning">
            {profileMsg}
        </div>
        </div>
    </div>        
  )
}
