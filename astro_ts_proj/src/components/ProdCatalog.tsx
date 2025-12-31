import { useEffect, useState } from 'react'
import axios from 'axios';

const api = axios.create({
  baseURL: "http://localhost:9090",
  headers: {'Accept': 'application/json',
            'Content-Type': 'image/png'}
})

interface Products {
  id: number,
  descriptions: string,
  qty: number,
  unit: string,
  sellprice: number,
  productpicture: string
}

interface Productdata {
  totpage: number,
  page: number,
  products: Products[]
}

const toDecimal = (number: number) => {
  const formatter = new Intl.NumberFormat('en-US', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2,
  });
  // Format the number
  return formatter.format(number);
};

 const ProdCatalog = () => {
    const [page, setPage] = useState(1);
    const [prods, setProds] = useState<Products[]>([]);
    const [totpage, setTotpage] = useState(0);
    const [message, setMessage] = useState<string>('');

    const fetchCatalog = (pg: number) => {
      setMessage('please wait...');
       api.get<Productdata>(`/take/products/list/${pg}`)
      .then((res) => {
        const jdata: Productdata = res.data;
        setProds(jdata.products);
        setTotpage(jdata.totpage);
        setPage(jdata.page);
      }, (error) => {
        if (error.message !== '') {
          setMessage(error.response.data.message);
        } else {
          setMessage(error.message);
        }
      });      

    }

    useEffect(() => {
      fetchCatalog(page)
        setMessage('');
    },[page, totpage, prods]);

    function firstPage() {
        let pg = page;
        pg = 1;
        setPage(pg);
        fetchCatalog(pg);
        return;    
      }
    
      function nextPage() {
        if (page === totpage) {
            return;
        }
        let pg = page;
        pg++;
        setPage(pg);
        fetchCatalog(pg);
        return;
      }
    
      function prevPage() {
        if (page === 1) {
          return;
          }
          let pg = page;
          pg--;
          setPage(pg);
          fetchCatalog(pg);
          return;    
      }
    
      function lastPage() {
        setPage(totpage);
        fetchCatalog(totpage);
        return;    
      }

    return(
    <div className="container bg-transparent mb-9">
            <h3 className='text-dark text-center'>Products Catalog</h3>            
            <div className="text-center text-warning">{message}</div>
            <div className="card-group mb-3">
            {prods.map((item) => {
                    return (
                      <div key={item['id']} className='col-md-4'>
                      <div className="card mx-3 mt-3 card-box">
                            <img src={`http://localhost:9090/products/${item['productpicture']}`}  className="card-img-top"  alt={'products'}/>
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
          </div>    

        <div className='container'>
        <nav aria-label="Page navigation example">
        <ul className="pagination">
          <li className="page-item"><button type="button" onClick={lastPage} className="page-link">Last</button></li>
          <li className="page-item"><button type="button" onClick={prevPage} className="page-link">Previous</button></li>
          <li className="page-item"><button type="button" onClick={nextPage} className="page-link">Next</button></li>
          <li className="page-item"><button type="button" onClick={firstPage} className="page-link">First</button></li>
          <li className="page-item page-link text-danger">Page&nbsp;{page} of&nbsp;{totpage}</li>
        </ul>
      </nav>
      <br/><br/>
      </div>
    </div>
    )
}

export default ProdCatalog;