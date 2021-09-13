import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Product } from '../model/product.model';

interface ProductResponseData {
  productId: string;
  productName: string;
  productCategory: string;
  productDesc: string;
  units: number;
}

@Injectable({ providedIn: 'root' })
export class HomeService {
  products: Product[];
  productChanged = new Subject<Product[]>();
  filteredProducts: Product[] = [];
  ROOT_URL:string='http://Airbusappservice-env.eba-wdga66sv.us-east-1.elasticbeanstalk.com';
  LOCAL_URL:string = 'http://localhost:8081';
  constructor(private http: HttpClient, private router: Router) {}

  fetchProducts() {
    return this.http
      .get<Product[]>(this.ROOT_URL+'/airbus/v1/products')
      .pipe(
        map((responseData) => {
          return responseData.map((product) => {
            return {
              ...product,
            };
          });
        }),
        tap((products) => {
          this.products = products.reverse();
          this.productChanged.next(this.products.slice());
        })
      );
  }

  addProduct(
    name: string,
    category: string,
    description: string,
    units: number
  ) {
    return this.http
      .post<ProductResponseData>(this.ROOT_URL+'/airbus/v1/products', {
        productName: name,
        productCategory: category,
        productDesc: description,
        units: units,
      })
      .pipe(
        catchError(this.handleError),
        tap((resData) => {
          this.products.unshift(resData);
          this.productChanged.next(this.products.slice());
        })
      );
  }

  updateProduct(
    index: number,
    id: string,
    name: string,
    category: string,
    description: string,
    units: number
  ) {
    return this.http
      .put<ProductResponseData>(this.ROOT_URL+'/airbus/v1/products', {
        productId: id,
        productName: name,
        productCategory: category,
        productDesc: description,
        units: units,
      })
      .pipe(
        catchError(this.handleError),
        tap((resData) => {
          if(this.filteredProducts.length == 0){
            this.products[index] = resData;
            this.productChanged.next(this.products.slice());
          }
          else{
            this.filteredProducts[index] = resData;
            const productIndex = this.products.findIndex(pdt => pdt.productId === resData.productId);
            this.products[productIndex] = resData;
            this.productChanged.next(this.filteredProducts.slice());
          }
        })
      );
  }

  searchProduct(category: string) {
    return this.http
      .get<Product[]>(
        this.ROOT_URL+'/airbus/v1/products/' + category.toLowerCase()
      )
      .pipe(
        map((responseData) => {
          return responseData.map((product) => {
            return {
              ...product,
            };
          });
        }),
        catchError(this.handleError),
        tap((products) => {
          this.products = products;
          this.productChanged.next(this.products.slice());
        })
      );
  }

  filterProducts(filterMap: Map<string, any>) {
    if (filterMap.size > 0) {
      console.log(this.products)
      this.filteredProducts = this.products.filter(product => {
        return ((filterMap.has('name')
          ? product.productName.toLowerCase().includes(filterMap.get('name').toLowerCase())
          : true) && (filterMap.has('category')
          ? product.productCategory.toLowerCase().includes(filterMap.get('category').toLowerCase())
          : true) && (filterMap.has('units')
          ? product.units === +filterMap.get('units')
          : true));
      });
    }else{
      this.filteredProducts = this.products;
    }
    this.productChanged.next(this.filteredProducts.slice());
  }

  private handleError(errorRes: HttpErrorResponse) {
    let errorMessage = 'An unknown error occured!';
    if (!errorRes.error) {
      return throwError(errorMessage);
    }
    switch (errorRes.error.message) {
      case 'USERNAME_ALREADY_EXIST':
        errorMessage = 'The username already exists';
        break;
      case 'EMAIL_ALREADY_EXIST':
        errorMessage = 'The email id already exists';
        break;
      case 'Bad credentials':
        errorMessage = 'Invalid credentials';
        break;
      case 'INVALID_CATEGORY':
        errorMessage = 'Invalid searched category';
    }
    return throwError(errorMessage);
  }
}
