Get All Documents
```
POST /msg-unknown/_search?typed_keys=true
{
  "query": {
    "match_all": {}
  }
}
```

Get All Messages in Header. sort by timestamp
sort by latest message
```
GET /msg-unknown/_search?typed_keys=true
{
  "size": 999,
  "query": {
    "term": {
      "msg.header.keyword": "ICICIT"
    }
  },
  "sort": [
    {
      "msg.timestamp": {
        "order": "desc"
      }
    }
  ]
}
```

Search Messages withing Header
Search Messages that contains exact substring
sort by latest message
```
GET /msg-unknown/_search?typed_keys=true
{
  "size": 100,
  "query": {
    "bool": {
      "must": [
        {
          "term": {
            "msg.header.keyword": "ICICIT"
          }
        },
        {
          "match_phrase": {
            "msg.body": "TNEB Electricity Bill for AutoPay Subscription for ONE97COM"
          }
        }
      ]
    }
  },
  "sort": [
    {
      "msg.timestamp": {
        "order": "desc"
      }
    }
  ]
}

```

Search Messages withing Header
Search Messages that contains most relevant substring
sort by score
```
GET /msg-unknown/_search?typed_keys=true
{
  "size": 999,
  "track_scores": true,
  "query": {
    "bool": {
      "must": [
        {
          "term": {
            "msg.header.keyword": "ICICIT"
          }
        },
        {
          "match": {
            "msg.body": "Your account will be debited with Rs 931.00 on 09-Oct-25 towards TNEB Electricity Bill for AutoPay Subscription for ONE97COM, RRN 505772771698-ICICI Bank." 
          }
        }
      ]
    }
  },
  "sort": [
    {
      "_score": {
        "order": "desc" // 'desc' is the default for _score, showing most relevant first
      }
    }
  ]
}
```