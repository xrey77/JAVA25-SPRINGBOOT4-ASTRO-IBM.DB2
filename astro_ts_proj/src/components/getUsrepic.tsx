import React from 'react';

const getUserpic = () => {

    const [userpic, setUserpic] = React.useState<string | null>(null);

    React.useEffect(() => {

        const timer = setInterval(() => {
          if (typeof window !== 'undefined' && window.sessionStorage) {            
            const upic = sessionStorage.getItem('USERPIC');
            if (upic) {
              setUserpic(upic);
            } 
          }

          }, 1000);        
          return () => clearInterval(timer);
    },[userpic]);
  
    return [{userpic: userpic}];
    
};

export default getUserpic;
