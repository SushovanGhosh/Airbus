import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Subscription, throwError } from 'rxjs';
import { AuthService } from '../auth/auth.service';
import { Filter } from '../model/filter.model';
import { Product } from '../model/product.model';
import { HomeService } from './home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent implements OnInit {
  @ViewChild('modalForm') productForm: NgForm;
  products: Product[];
  selectedProduct: Product;
  showModal: boolean = false;
  isEdit = false;
  index: number;
  subscription: Subscription;
  error: string = '';
  filterProductName: string;
  filterCategory: string;
  filterUnits: string;
  filterList: Map<string,any> = new Map();
  page:number = 1;
  itemsPerPage = 10;
  totalRecords: number
  constructor(private http: HttpClient, private homeService: HomeService) {}

  ngOnInit(): void {
    this.subscription = this.homeService.productChanged.subscribe(
      (products: Product[]) => {
        this.products = products;
      }
    );
    this.homeService.fetchProducts().subscribe();
    this.totalRecords = this.products ?.length;
  }

  openModal() {
    this.showModal = true;
  }

  hideModal() {
    this.showModal = false;
    this.isEdit = false;
    this.productForm.reset();
  }

  onSubmit(form: NgForm) {
    this.error=""
    if (!form.valid) {
      return;
    }
    const productName = form.value.name;
    const productCategory = form.value.category;
    const productDesc = form.value.description;
    const units = form.value.units;
    if (!this.isEdit){
      this.homeService
        .addProduct(productName, productCategory, productDesc, units)
        .subscribe();
        this.filterProductName = '';
        this.filterCategory = '';
        this.filterUnits = '';
        this.page = 1;
    }
    else {
      this.homeService
        .updateProduct(
          this.index,
          this.selectedProduct.productId,
          productName,
          productCategory,
          productDesc,
          units
        )
        .subscribe();
      this.selectedProduct = null;
      
    }
    this.hideModal();
    form.reset();
  }

  onEdit(index: number) {
    this.isEdit = true;
    this.openModal();
    this.index = index + (this.itemsPerPage*(this.page-1));
    this.selectedProduct = this.products[this.index];
    this.productForm.setValue({
      name: this.selectedProduct.productName,
      category: this.selectedProduct.productCategory,
      description: this.selectedProduct.productDesc,
      units: this.selectedProduct.units,
    });
  }


  onSearch(event: { target: { value: any; }; }) {
    this.error=""
    const category:string = event.target.value;
    if (category.trim() === '') this.homeService.fetchProducts().subscribe();
    else this.homeService.searchProduct(category).subscribe(data =>{
      this.products = data
    },error =>{
      this.error = error;
    });
  }

  onFilter(filterKey: string, event: { target: { value: any; }; }) {
    const filterValue:string = event.target.value;
    this.page = 1;
    if(filterValue.trim() === ""){
      this.filterList.delete(filterKey);
    }else{
      this.filterList.set(filterKey,filterValue);
    }
    this.homeService.filterProducts(this.filterList);
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
    }
    return throwError(errorMessage);
  }
}
