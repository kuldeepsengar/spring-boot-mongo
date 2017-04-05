## Modeling in MongoDB

### Denormalizing Relationships in Mongo

1. Full embedded object :
```json
{
    "_id":"123",
    "first_name":"kuldeep",
    "address":{
        "first_line":"blah",
        "city":"Pune"
    }

}
```
* It should mostly be used for one-to-one mapping. 
* For one-to-many, check the cardinality.
    * If the reference object cannot have independent identity. 
* For one-to-many, if you have a definitive "N" ( like nationality )

2. Reference
```json
//person
{
    "_id":"123",
    "first_name":"kuldeep",
    "address":DBRef("address","1")
}
// address
{
    "_id":"1",
    "first_line":"blah",
    "city":"Pune"
}
```
* It should be used for mostly one-to-many mapping.

3. Partial
```json
//person
{
    "_id":"123",
    "first_name":"kuldeep",
    "address":{"_id":"1","city":"Pune"}
}
// address
{
    "_id":"1",
    "first_line":"blah",
    "city":"Pune"
}
```
* For one-to-many mapping, where you might need data upfront and then lazily fetch more details. 
