const axios = require('axios');

describe('Users', () => {
    describe('POST User', () => {
        it('should create an user object', () => {
            axios.post('http://192.168.99.100:5678/user?password=122&first_name='+
            'qqws&last_name=aSSA&email=ZXZX&sex=1&weight=55&height=170&allergies='+
            '-&calories_intake_daily=1111&weight_goal=60&user_name=lupinka&birthDate=4.12.1998')
                .then((res) => {
                    expect(res.status).toBe(201);
                })
                .catch((e) => {
                    //console.log(e);
                });
        });
    });

    describe('GET User', function() {
        var i
        for(i=1;i<100000;i++){
            it('should get an user object',()=>{
                axios.get('http://192.168.99.100:5678/user?id=4')
                .then((res) => {
                    expect(res.status).toBe(200);
                })
                .catch((e) => {
                });
                
            })
        }
         
    })