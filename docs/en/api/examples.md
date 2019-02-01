# API usage examples

### Log in
#### Example with Curl

> 

```bash
curl -X POST -H "X-Requested-With: XMLHttpRequest" -H "Content-Type: application/json" -H "Cache-Control: no-cache" -d '{  
    "username": "USERNAME",
    "password": "PASSWORD"}' "http://localhost:9966/api/auth/login"
```


#### Example with Python
```python 
import requests
import json

response = requests.post("http://localhost:9966/api/auth/login",
                         headers={'X-Requested-With': 'XMLHttpRequest'},
                         json={"username": USERNAME, "password": PASSWORD})
response = response.json()
token = response['token']
``` 