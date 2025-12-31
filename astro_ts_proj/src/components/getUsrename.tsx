import React from 'react';

const getUsername = () => {
  const [username, setUsername] = React.useState<string | null>(null);
  
   React.useEffect(() => {

        const timer = setInterval(() => {
          if (typeof window !== 'undefined' && window.sessionStorage) {          
            const uname = sessionStorage.getItem('USERNAME');
            if (uname) {
              setUsername(uname);    
            } 
          }
          }, 1000);        
          return () => clearInterval(timer);
    },[username]);
    return [{username: username}];
};

export default getUsername;
