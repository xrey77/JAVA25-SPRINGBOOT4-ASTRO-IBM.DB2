import { useEffect, useState } from 'react'
import axios from 'axios';

const api = axios.create({
  baseURL: "http://localhost:9090",
  headers: {'Accept': 'application/json',
            'Content-Type': 'application/json'}
})

interface Products {
  id: number,
  descriptions: string,
  qty: number,
  unit: string,
  sellprice: number
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
  return formatter.format(number);
};


function ProdList() {
    const [page, setPage] = useState<number>(1);
    const [totpage, setTotpage] = useState<number>(0);
    const [products, setProducts] = useState<any[]>([]);
    const [message, setMessage] = useState<string>('');
    const [totalrecords, setTotalrecords] = useState<number>(0);

    const fetchProducts = async (pg: any) => {
      // setMessage('please wait...');
      await api.get<Productdata>(`/take/products/list/${pg}`)
      .then((response: any) => {
        const jdata: Productdata = response.data;
        setProducts(jdata.products);
        setPage(jdata.page);
        setTotpage(jdata.totpage);
        setTotalrecords(jdata.totalrecords);
      }, (error: any) => {
          if (error.message === null) {
            setMessage(error.message);
          } else {
              setMessage(error.response.data.message);
          }
              setTimeout(() => {
                setMessage('');
              }, 3000);
      });      
    }

    useEffect(() => {
      fetchProducts(page);
      setTimeout(() => {
        setMessage('');
      }, 3000);
   },[page]);

    const firstPage = (event: any)  => {
        event.preventDefault();
        let pg = page;
        pg = 1;        
        fetchProducts(pg);
        return;    
      }
    
      const nextPage = (event: any)  => {
        event.preventDefault()
        if (page === totpage) {
            return;
        }
        let pg = page;
        pg++;
        setPage(pg);
        fetchProducts(pg);
        return;
      }
    
      const prevPage = (event: any)  => {
        event.preventDefault();
        if (page === 1) {
          return;
          }
          let pg = page;
          pg--;
          fetchProducts(pg);
          return;    
      }
    
      const lastPage = (event: any)  => {
        event.preventDefault();
        fetchProducts(totpage);
        return;    
      }

    return(
    <div className="container">
            <h1 className='text-dark'>Products List</h1>
            <div className='text-danger xtop'>{message}</div>
            <table className="table table-danger table-striped mt-4">
            <thead>
                <tr>
                <th className='bg-warning' scope="col">#</th>
                <th className='bg-warning' scope="col">Descriptions</th>
                <th className='bg-warning' scope="col">Qty</th>
                <th className='bg-warning' scope="col">Unit</th>
                <th className='bg-warning' scope="col">Price</th>
                </tr>
            </thead>
            <tbody>

            {products.map((item) => {
            return (
              <tr key={item['id']}>
                 <td>{item['id']}</td>
                 <td>{item['descriptions']}</td>
                 <td>{item['qty']}</td>
                 <td>{item['unit']}</td>
                 <td>&#8369;{toDecimal(item['sellprice'])}</td>
               </tr>
              );
        })}
            </tbody>
            </table>

        <nav aria-label="Page navigation example">
        <ul className="pagination">
          <li className="page-item"><button type="button" onClick={lastPage} className="page-link" >Last</button></li>
          <li className="page-item"><button type="button" onClick={prevPage} className="page-link" >Previous</button></li>
          <li className="page-item"><button type="button" onClick={nextPage} className="page-link" >Next</button></li>
          <li className="page-item"><button type="button" onClick={firstPage} className="page-link" >First</button></li>
          <li className="page-item page-link text-danger">Page&nbsp;{page} of&nbsp;{totpage}</li>
        </ul>
      </nav>
      <div className="text-dark">Total Records :&nbsp;{totalrecords}</div>
  </div>
  )
}
export default ProdList;