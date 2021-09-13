export class Product {
    public productId: string;
    public productName: string;
    public productCategory: string;
    public productDesc: string;
    public units: number;

    constructor(productId: string, productName: string, productCategory: string, productDesc: string, units: number) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productDesc = productDesc;
        this.units = units;
    }
}