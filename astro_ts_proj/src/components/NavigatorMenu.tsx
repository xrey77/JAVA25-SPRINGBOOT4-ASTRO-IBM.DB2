import React from 'react';

const NavigatorMenu = () => {
  const [username, setUsername] = React.useState<string | null>('');
  const [userpic, setUserpic] = React.useState<string | null>('');

  React.useEffect(() => {
    setInterval(() => {
      if (typeof window !== 'undefined' && window.sessionStorage) {          
        const uname = window.sessionStorage.getItem('USERNAME');
        if (uname !== null) {
          setUsername(uname);      
        } 
        const upic = window.sessionStorage.getItem('USERPIC');
        if (upic !== null) {
          setUserpic(upic);
        }    
      }
      }, 1000);        


  },[]);

  function logout() {
    window.sessionStorage.removeItem('USERID');
    window.sessionStorage.removeItem('USERNAME');
    window.sessionStorage.removeItem('USERPIC');
    window.sessionStorage.removeItem('TOKEN');
    setUsername('')
    setUserpic('')
    location.href = "/";
    location.reload();
  }


  return (
    <>
    <nav className="navbar navbar-expand-lg hdr">
      <div className="container-fluid">
        <a className="navbar-brand text-danger" href="/"><strong><img className="logo" src="http://localhost:9090/images/logo.png" alt=""/></strong></a>
          <button className="navbar-toggler" type="button"  data-bs-toggle="offcanvas" data-bs-target="#offcanvasMenu" aria-controls="offcanvasWithBothOptions">
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarSupportedContent">
          <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
              <a className="nav-link text-dark embossed-text" aria-current="page" href="/about">About Us</a>
            </li>
            <li className="nav-item dropdown">
              <a className="nav-link dropdown-toggle text-dark embossed-text" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                Products
              </a>
              <ul className="dropdown-menu">
                <li><a className="dropdown-item" href="/productlist">Products List</a></li>
                <li><a className="dropdown-item" href="/productcatalog">Products Catalog</a></li>
                <li><hr className="dropdown-divider"/></li>
                <li><a className="dropdown-item" href="/productsearch">Product Search</a></li>
              </ul>
            </li>
            <li className="nav-item">
              <a className="nav-link text-dark embossed-text" href="/contact">Contact Us</a>
            </li>
          </ul>

          { username === '' ? (
          <ul className="navbar-nav mr-auto">
            <li className="nav-item">
              <a className="nav-link text-dark embossed-text" href="#" data-bs-toggle="modal" data-bs-target="#staticLogin">Login</a>
            </li>
            <li className="nav-item">
              <a className="nav-link text-dark embossed-text" href="#" data-bs-toggle="modal" data-bs-target="#staticRegister">Register</a>
            </li>
          </ul>
          ) : (

            <ul className="nav">  
            <li className="nav-item dropdown">
              <a className="nav-link dropdown-toggle" href="/#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                <img className="user" src={userpic + ""} alt=""/>&nbsp;{username}
              </a>
              <ul className="dropdown-menu">
                <li data-bs-dismiss="offcanvas">
                  <a onClick={logout} className="dropdown-item" href="/#">Logout</a></li>
                <li data-bs-dismiss="offcanvas">
                  <a className="dropdown-item" href="/profile">Profile</a></li>
                <li><hr className="dropdown-divider"/></li>
                <li data-bs-dismiss="offcanvas">
                  <a className="dropdown-item" href="/#">Messenger</a></li>
              </ul>
            </li>
          </ul>           

          )}

        </div>
      </div>
    </nav>

    <div className="offcanvas offcanvas-end hdr" data-bs-scroll="true" id="offcanvasMenu" aria-labelledby="offcanvasWithBothOptionsLabel">
      <div className="offcanvas-header bg-primary">
        <h5 className="offcanvas-title text-white" id="offcanvasWithBothOptionsLabel">Drawer Menu</h5>
        <button type="button" className="btn-close btn-close-white" data-bs-dismiss="offcanvas" aria-label="Close"></button>
      </div>
      <div className="offcanvas-body hdr">

        <ul className="nav flex-column">
          <li className="nav-item" data-bs-dismiss="offcanvas">
            <a className="nav-link emboss-menu" aria-current="page" href="/about">About Us</a>
          </li>
          <li className="nav-item"><hr/></li>
          <li className="nav-item dropdown">
                <a className="nav-link dropdown-toggle emboss-menu" href="/#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                  Products
                </a>
                <ul className="dropdown-menu">
                  <li data-bs-dismiss="offcanvas"><a className="dropdown-item" href="/productlist">Products List</a></li>
                  <li><hr className="dropdown-divider"/></li>
                  <li data-bs-dismiss="offcanvas"><a className="dropdown-item" href="/productcatalog">Products Catalog</a></li>
                  <li><hr className="dropdown-divider"/></li>
                  <li data-bs-dismiss="offcanvas"><a className="dropdown-item" href="/productsearch">Product Search</a></li>
                </ul>
            </li>

            <li className="nav-item"><hr/></li>
            <li className="nav-item" data-bs-dismiss="offcanvas">
              <a className="nav-link emboss-menu" aria-current="page" href="/contact">Contact Us</a>
            </li>
            <li className="nav-item"><hr/></li>

            </ul>

            { username === '' ? (
                  <ul className="nav flex-column">
                  <li className="nav-item" data-bs-dismiss="offcanvas">
                    <a className="nav-link emboss-menu" href="/#" data-bs-toggle="modal" data-bs-target="#staticLogin">Login</a>
                  </li>
                  <li className="nav-item"><hr/></li>
                  <li className="nav-item" data-bs-dismiss="offcanvas">
                    <a className="nav-link emboss-menu" href="/#" data-bs-toggle="modal" data-bs-target="#staticRegister">Register</a>
                  </li>            
                </ul>

            ) : (
               <ul className="navbar-nav mr-auto">              
                  <li className="nav-item dropdown">
                    <a className="nav-link dropdown-toggle active" href="/#" role="button" data-bs-toggle="dropdown" aria-expanded="false">                   
                        <img className="user" src={userpic + ""} />&nbsp;{ username}
                    </a>
                    <ul className="dropdown-menu">
                      <li data-bs-dismiss="offcanvas">
                        <a onClick={logout} className="dropdown-item" href="/#">LogOut</a>
                      </li>
                      <li className="nav-item"><hr/></li>
                      <li data-bs-dismiss="offcanvas">
                        <a className="dropdown-item" href="/profile">Profile</a> 
                      </li>
                      <li><hr className="dropdown-divider"/></li>
                      <li data-bs-dismiss="offcanvas">
                        <a className="dropdown-item" href="/#">Messenger</a>
                      </li>
                    </ul>
                  </li>          
                  <li className="nav-item"><hr/></li>                                        
                </ul>       

            )
          }



                
      </div>
      </div>
  </>
  )
}

export default NavigatorMenu;