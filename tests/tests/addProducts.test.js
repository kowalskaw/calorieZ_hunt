const axios = require('axios');
describe('GET Product', function() {
    it('should get a product object',()=>{
        axios.get('http://192.168.99.100:5678/product?id=4')
        .then((res) => {
            expect(res.status).toBe(200);
        })
        .catch((e) => {
        });
        
    })
})