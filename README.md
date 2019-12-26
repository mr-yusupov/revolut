# API
GET: `/account/{accountId}/balance` - balance of given account.  
POST: `/transaction/` - creates transaction for a new transfer.  
POST: `/account/transfer` - request body should be `json` formatted - executes a transfer between accounts.
Example of request body:
`{"senderId": 123, "receiverId": 456, "amount": 10, "requestId":"asdf345-345345kj-345etjh56"}
`