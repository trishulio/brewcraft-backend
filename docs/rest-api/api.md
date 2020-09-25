# REST Standard

This section describes the rules/standards of RESTful used in this API reference.

## GET

- Limited to "read" operations

- Defines the resource by URL

- Defines options, filters, actions, etc. using the path parameters

- Path URL pattern: `/api/resources` returns a list of objects

- Path URL pattern: `/api/resources/resource_id` returns a single object

- Idempotent

## POST

- Limited to "create" operations (not update)

- Defines the resource by URL

- Defines options, filters, actions, etc. using the path parameters

- Defines object content using the request body

- Request Body (representing any entity shall never contain the ID for the object)

- Response should always contain the ID only newly created resource

- Request should always be made to `/api/resources` and never to `/api/resources/resource_id`

- The response body shall always return a JSON array with list of IDs of resources created (this can accomodate batch updates)

- Non Idempotent

## PUT

- Limited to "create" and "update" operations

- Defines the resource by URL

- Defines options, filters, actions, etc. using the path parameters

- The request body shall not contain the resource ID.

- The request body can contain any subset of the fields in the resource (only those fields will be updated)

- Request should be made to `/api/resources/resource_id` to update a single instance.

- Request made to `/api/resources/` shall have a response body with list of resource objects (resource_id is mandatory field)

- The response body shall always return a JSON array with list of IDs of resources created (this can accomodate batch updates)

- Idempotent

## DELETE

- Limited to "delete" operations only.

- Defines options, filters, actions, etc. using the path parameters

- Shall contain NO request body

- Request should always be made to `/api/resources/resource_id` to delete a single instance.

- Request should always be made to `/api/resources` to delete a multiple instance with request body containing JSON array with list of IDs

- The response should contain the IDs of resources deleted.

- Idempotent

# Raw Materials

```
GET /api/inventory/materials/raw?q=query&attr=name,type,cost,supplier_name,qty&offset=0&limit=20&sort=name&order_asc=true
```

```
GET /api/analytics/inventory/materials/raw?q=query&attr=cost,month,year&aggr=total&aggr_field=cost&group_by=month,year&offset=0&limit=20&sort=name&order_asc=true
```

# CRUD for all resources

## Endpoints

```
GET/POST/PUT/DELETE /api/v1/suppliers/
GET/POST/PUT/DELETE /api/v1/suppliers/:supplier_id

GET/POST/PUT/DELETE /api/v1/purchases
GET/POST/PUT/DELETE /api/v1/purchases/:purchase_order_id/
GET/POST/PUT/DELETE /api/v1/purchases/:purchase_order_id/invoice

GET/POST/PUT/DELETE /api/v1/breweries/
GET/POST/PUT/DELETE /api/v1/breweries/:brewery_id

GET/POST/PUT/DELETE /api/v1/breweries/:brewery_id/equipments
GET/POST/PUT/DELETE /api/v1/breweries/:brewery_id/equipments/:equipment_id

GET/POST/PUT/DELETE /api/v1/users
GET/POST/PUT/DELETE /api/v1/users/:user_id

GET/POST/PUT/DELETE /api/v1/inventory/materials/
GET/POST/PUT/DELETE /api/v1/inventory/materials/raw
GET/POST/PUT/DELETE /api/v1/inventory/materials/raw/:material_id
GET/POST/PUT/DELETE /api/v1/inventory/materials/raw/used
GET/POST/PUT/DELETE /api/v1/inventory/materials/raw/wasted

GET/POST/PUT/DELETE /api/v1/inventory/materials/wip/product
GET/POST/PUT/DELETE /api/v1/inventory/materials/wip/extract

GET/POST/PUT/DELETE /api/v1/inventory/materials/finished

GET/POST/PUT/DELETE /api/v1/inventory/packaging/
GET/POST/PUT/DELETE /api/v1/inventory/packaging/used
GET/POST/PUT/DELETE /api/v1/inventory/packaging/wasted

GET/POST/PUT/DELETE /api/v1/brews/
GET/POST/PUT/DELETE /api/v1/brews/:brew_id/
GET/POST/PUT/DELETE /api/v1/brews/:brew_id/logs
GET/POST/PUT/DELETE /api/v1/brews/:brew_id/logs/:log_id
```

## DTOs

### Supplier

Response Body for: `/api/v1/suppliers/:supplier_id`

```
{
    "id": "supplier_id",
    "name": "",
    "created": "2020-08-10T06:32:23.0000+00",
    "last_updated": "2020-08-10T06:32:23.0000+00",
    "address": {
        "country": "Canada",
        "city": "Vancouver",
        "care_of": "",
        "street_number": "12345",
        "street_name": "Main Avenue",
        "postal_code": "V1A 7H4",
        "po_box": "?"
    }
}
```

### Purchase Order

Response Body for: `purchases/:purchase_order_id`

```
{
    "id": "12354",
    "qty": 123,
    "qty_unit": "KG",
    "amt": 100,
    "shipping": 50,
    "status": "delivered",
    "date": "2020-08-10T06:32:23.0000+00",
    "currency": "CAD",
    "lot": "3254454",
    "total": 34454,
    "supplier": {
        "id": "supplier_id",
        "name": "",
        "created": "2020-08-10T06:32:23.0000+00",
        "last_updated": "2020-08-10T06:32:23.0000+00",
        "address": {
            "country": "Canada",
            "city": "Vancouver",
            "care_of": "",
            "street_number": "12345",
            "street_name": "Main Avenue",
            "postal_code": "V1A 7H4",
            "po_box": "?"
        }
    }
}
```

### Brewery

Response Body for: `/api/v1/breweries/:brewery_id`

```
{
    "id": "abc1234", 
    "name": "Brewery ABC",
    "created": "2020-08-10T06:32:23.0000+00",
    "last_updated": "2020-08-10T06:32:23.0000+00",
    "equipment_count": 234,
    "user_count": 2123,
    "address": {
        "country": "Canada",
        "city": "Vancouver",
        "care_of": "",
        "street_number": "12345",
        "street_name": "Main Avenue",
        "postal_code": "V1A 7H4",
        "po_box": "?"
    }
}
```

### User

Response Body for: `/api/v1/users/:user_id`

```
{
    "id": "abc1235",
    "f_name": "John",
    "l_name": "Doe",
    "email": "john.doe@example.com",
    "last_updated": "2020-08-10T06:32:23.0000+00",
    "brewery": {
        "id": "abc1234", 
        "name": "Brewery ABC",
        "created": "2020-08-10T06:32:23.0000+00",
        "last_updated": "2020-08-10T06:32:23.0000+00",
        "equipment_count": 234,
        "user_count": 2123,
        "address": {
            "country": "Canada",
            "city": "Vancouver",
            "care_of": "",
            "street_number": "12345",
            "street_name": "Main Avenue",
            "postal_code": "V1A 7H4",
            "po_box": "?"
        }
    } 
}
```

### Raw Material

Response Body for: `/api/v1/inventory/materials/raw/:material_id`

```
{
    "id": "abc1234",
    "brand": "Best Materials",
    "type": "t1",
    "subtype": "s1",
    "description": "Some text here",
    "brew": {
        "id": "1234abc",
        "type": "",
        "status": "fermentation",
        "recorded_start_date": "",
        "expected_end_time": "",
        "recorded_end_date": "",
        "brewery": {
            "id": "abc1234", 
            "name": "Brewery ABC",
            "created": "2020-08-10T06:32:23.0000+00",
            "last_updated": "2020-08-10T06:32:23.0000+00",
            "equipment_count": 234,
            "user_count": 2123,
            "address": {
                "country": "Canada",
                "city": "Vancouver",
                "care_of": "",
                "street_number": "12345",
                "street_name": "Main Avenue",
                "postal_code": "V1A 7H4",
                "po_box": "?"
            }
        }
    },
    "purchase_order": {
        "id": "12354",
        "qty": 123,
        "qty_unit": "KG",
        "amt": 100,
        "shipping": 50,
        "status": "delivered",
        "date": "2020-08-10T06:32:23.0000+00",
        "currency": "CAD",
        "lot": "3254454",
        "total": 34454,
        "supplier": {
            "id": "supplier_id",
            "name": "",
            "created": "2020-08-10T06:32:23.0000+00",
            "last_updated": "2020-08-10T06:32:23.0000+00",
            "address": {
                "country": "Canada",
                "city": "Vancouver",
                "care_of": "",
                "street_number": "12345",
                "street_name": "Main Avenue",
                "postal_code": "V1A 7H4",
                "po_box": "?"
            }
        }
    }
}
```

### Brew

Response Body for: `/api/v1/brews/:brew_id/`

```
{
    "id": "1234abc",
    "type": "",
    "status": "fermentation",
    "recorded_start_date": "2020-08-10T06:32:23.0000+00",
    "expected_end_time": "2020-08-10T06:32:23.0000+00",
    "recorded_end_date": "2020-08-10T06:32:23.0000+00",
    "brewery": {
        "id": "abc1234", 
        "name": "Brewery ABC",
        "created": "2020-08-10T06:32:23.0000+00",
        "last_updated": "2020-08-10T06:32:23.0000+00",
        "equipment_count": 234,
        "user_count": 2123,
        "address": {
            "country": "Canada",
            "city": "Vancouver",
            "care_of": "John Doe",
            "street_number": "12345",
            "street_name": "Main Avenue",
            "postal_code": "V1A 7H4",
            "po_box": "?"
        }
    }
}
```

### Brew Log

Response Body for: `/api/v1/brews/:brew_id/logs/:log_id`

```
{
    "id": "1234abc",
    "date_time": "2020-08-10T06:32:23.0000+00",
    "notes": "Some notes text here",
    "material": {
        "id": "abc1234",
        "brand": "Best Materials",
        "type": "t1",
        "subtype": "s1",
        "description": "Some text here",
        "brew": {
            "id": "1234abc",
            "type": "t1",
            "status": "fermentation",
            "recorded_start_date": "2020-08-10T06:32:23.0000+00",
            "expected_end_time": "2020-08-10T06:32:23.0000+00",
            "recorded_end_date": "2020-08-10T06:32:23.0000+00",
            "brewery": {
                "id": "abc1234", 
                "name": "Brewery ABC",
                "created": "2020-08-10T06:32:23.0000+00",
                "last_updated": "2020-08-10T06:32:23.0000+00",
                "equipment_count": 234,
                "user_count": 2123,
                "address": {
                    "country": "Canada",
                    "city": "Vancouver",
                    "care_of": "John Doe",
                    "street_number": "12345",
                    "street_name": "Main Avenue",
                    "postal_code": "V1A 7H4",
                    "po_box": "?"
                }
            }
        },
        "purchase_order": {
            "id": "12354",
            "qty": 123,
            "qty_unit": "KG",
            "amt": 100,
            "shipping": 50,
            "status": "delivered",
            "date": "2020-08-10T06:32:23.0000+00",
            "currency": "CAD",
            "lot": "3254454",
            "total": 34454,
            "supplier": {
                "id": "supplier_id",
                "name": "John Doe Enterprises",
                "created": "2020-08-10T06:32:23.0000+00",
                "last_updated": "2020-08-10T06:32:23.0000+00",
                "address": {
                    "country": "Canada",
                    "city": "Vancouver",
                    "care_of": "John Doe",
                    "street_number": "12345",
                    "street_name": "Main Avenue",
                    "postal_code": "V1A 7H4",
                    "po_box": "?"
                }
            }
        }
    },
    "wasted_material": {}, // TODO
    "extract": {}, // TODO
    "wasted_product": {}, // TODO
    "user": {
        "id": "abc1235",
        "f_name": "John",
        "l_name": "Doe",
        "email": "john.doe@example.com",
        "last_updated": "2020-08-10T06:32:23.0000+00",
        "brewery": {
            "id": "abc1234", 
            "name": "Brewery ABC",
            "created": "2020-08-10T06:32:23.0000+00",
            "last_updated": "2020-08-10T06:32:23.0000+00",
            "equipment_count": 234,
            "user_count": 2123,
            "address": {
                "country": "Canada",
                "city": "Vancouver",
                "care_of": "John Doe",
                "street_number": "12345",
                "street_name": "Main Avenue",
                "postal_code": "V1A 7H4",
                "po_box": "?"
            }
        } 
    }
}
```