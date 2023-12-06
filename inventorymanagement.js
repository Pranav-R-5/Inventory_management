db.createCollection("products");

db.getCollection("products").insertMany([
    {
        "p_id":"1",
        "p_name":"iphone",
        "price":65675,
        "quantity":775,
        "date_added":Date("2023-12-12"),
        "created_by":"admin",
        "updated_by":"admin",
        "updated_date":Date("2023-13-13")
    },
    {
        "p_id":"2",
        "p_name":"iwatch",
        "price":6000,
        "quantity":75,
        "date_added":Date("2023-12-12"),
        "created_by":"admin",
        "updated_by":"admin",
        "updated_date":Date("2023-13-13")
    },
    {
        "p_id":"3",
        "p_name":"maac",
        "price":120000,
        "quantity":70,
        "date_added":Date("2023-12-12"),
        "created_by":"admin",
        "updated_by":"admin",
        "updated_date":Date("2023-13-13")
    },
    {
        "p_id":"4",
        "p_name":"ipad",
        "price":40001,
        "quantity":123,
        "date_added":Date("2023-12-12"),
        "created_by":"admin",
        "updated_by":"admin",
        "updated_date":Date("2023-13-13")
    },
    {
        "p_id":"5",
        "p_name":"airtag",
        "price":12000,
        "quantity":71,
        "date_added":Date("2023-12-12"),
        "created_by":"admin",
        "updated_by":"admin",
        "updated_date":Date("2023-13-13")
    },
    {
        "p_id":"6",
        "p_name":"case",
        "price":1500,
        "quantity":600,
        "date_added":Date("2023-12-12"),
        "created_by":"admin",
        "updated_by":"admin",
        "updated_date":Date("2023-13-13")
    }

]
);

db.getCollection("products").find({})


db.getCollection("products").find({p_name:"iwatch"})

db.getCollection("products").find({price:{$gte: 12000}})

db.getCollection("products").find({price:{$gte: 12000,$gte:120000}})

const productCount=db.getCollection("products").aggregate([{
    $group:{
        _id:null,
        count:{$sum:1}
    }
}]
)
printjson(productCount.toArray())

db.product_details.find({ prod_price: { $gt: 10000 } }).count()

db.product_details.updateOne(
    { p_id: "2" },
    { $set: { prod_quantity: 60 } }
);
