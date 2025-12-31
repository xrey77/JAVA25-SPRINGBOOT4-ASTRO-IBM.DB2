import React, { useState } from 'react'
import axios from 'axios';
import { $totalrecords } from '../store';
import { useStore } from '@nanostores/react';

const api = axios.create({
  baseURL: "http://localhost:9090",
  headers: {'Accept': 'application/json',
            'Content-Type': 'application/json'}
})

const toDecimal = (number: number) => {
  const formatter = new Intl.NumberFormat('en-US', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });
  // Format the number
  return formatter.format(number);
};

// interface Products {
//   id: number,
//   descriptions: string,
//   qty: number,
//   unit: string,
//   sellPrice: number,
//   productPicture: string
// }

// interface Productdata {
//   [x: string]: number;
//   products: Products[]
// }

const ProdSearch = () => {
    const [prodsearch, setProdsearch] = useState<any[]>([]);
    const [message, setMessage] = useState('');
    const [searchkey, setSearchkey] = useState('');
    const [page, setPage] = useState<number>(1);
    const [totpage, setTotpage] = useState<number>(0);
    const totalrecords = useStore($totalrecords);

     function getProdsearch(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault();
        setMessage("please wait...");
        api.get(`/take/products/search/${page}/${searchkey}`)
        .then((res: any) => {
            setTotpage(res.data.totpage);        
            setPage(res.data.page);  
            setProdsearch(res.data.products);
            $totalrecords.set(res.data.totalrecords);
        }, (error: any) => {
            if (error.response) {
              setMessage(error.response.data.message);
            } else {
              setMessage(error.message);
            }
            setTimeout(() => {
              setProdsearch([]);
              setMessage('');
              $totalrecords.set(0);
            }, 3000);
            return;
        });  
        setMessage('');
    }
     
    function getProducts(pg: any, key: any) {
      api.get(`/take/products/search/${pg}/${key}`)
      .then((res: any) => {
          setTotpage(res.data.totpage);          
          setProdsearch(res.data.products);
          setPage(res.data.page);          
          $totalrecords.set(res.data.totalrecords);
      }, (error: any) => {
          if (error.response) {
            setMessage(error.response.data.message);
          } else {
            setMessage(error.message);
          }
          setTimeout(() => {
            setProdsearch([]);
            setMessage('');
            $totalrecords.set(0);
          }, 3000);
          return;
      });  
      setMessage('');
  }

    function firstPage() {
      let pg = page;
      pg = 1;
      setPage(pg);
      getProducts(pg,searchkey );
      return;    
    }
  
    function nextPage() {
      if (page === totpage) {
          return;
      }
      let pg = page;
      pg++;
      setPage(pg);
      getProducts(pg,searchkey );
      return;
    }
  
    function prevPage() {
      if (page === 1) {
        return;
        }
        let pg = page;
        pg--;
        setPage(pg);
        getProducts(pg,searchkey );
        return;    
    }
  
    function lastPage() {
      setPage(totpage);
      getProducts(page,searchkey );
      return;    
    }    
  return (
    <div className="container mb-9">
        <h2 className='text-dark'>Products Search</h2>
        <div className='text-danger'>{message}</div>
        <form onSubmit={getProdsearch} autoComplete='off'>
            <div className="col-auto">
              <input type="text" required value={searchkey} onChange={e => setSearchkey(e.target.value)} className="form-control-sm" placeholder="enter Product keyword"/>
            </div>
            <div className="col-auto">
              <button type="submit" className="btn btn-primary mt-2 btn-sm mb-3">search</button>
            </div>
        </form>
        <div className="container mb-9">
          <div className="card-group">
        {prodsearch.map((item) => {
                return (
                <div key={item['id']}  className='col-md-4'>
                <div className="card mx-3 mt-3 card-box">
                    <img src={`http://localhost:9090/products/${item['productpicture']}`} className="card-img-top product-size" alt={""}/>
                    <div className="card-body">
                      <h5 className="card-title">Descriptions</h5>
                      <p className="card-text desc-h">{item['descriptions']}</p>
                    </div>
                    <div className="card-footer">
                      <p className="card-text text-danger"><span className="text-dark">PRICE :</span>&nbsp;<strong>&#8369;{toDecimal(item['sellprice'])}</strong></p>
                    </div>  
                </div>
                
                </div>
          );    
        })}
       {
        totalrecords > 5 ?
       <>
      <nav aria-label="Page navigation example">
        <ul className="pagination mt-3 mx-4">
          <li className="page-item"><button type="button" onClick={lastPage} className="page-link" >Last</button></li>
          <li className="page-item"><button type="button" onClick={prevPage} className="page-link" >Previous</button></li>
          <li className="page-item"><button type="button" onClick={nextPage} className="page-link" >Next</button></li>
          <li className="page-item"><button type="button" onClick={firstPage} className="page-link" >First</button></li>
          <li className="page-item page-link text-danger">Page&nbsp;{page} of&nbsp;{totpage}</li>
        </ul>
      </nav>
      <div className="text-white">Total Records :&nbsp;{totalrecords}</div>
      </>        :
        null
       }
          </div>          
          <br/><br/><br/>
        </div>

    </div>
  )
}

export default ProdSearch;