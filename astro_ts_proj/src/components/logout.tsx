const logout = () => {
    
    function close() {
      sessionStorage.removeItem('USERID');
      sessionStorage.removeItem('USERNAME');
      sessionStorage.removeItem('USERPIC');
      sessionStorage.removeItem('TOKEN');
      location.href = "/";
    }
    return close();
};
export default logout;
