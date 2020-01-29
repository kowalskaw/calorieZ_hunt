const axios = require('axios');

// describe('Users', () => {
//     describe('POST User', () => {
//         it('should create an user object', () => {
//             const user = {
//                 'id': '55',
//                 'first_name': 'Dominik',
//                 'last_name': 'XYZ',
//                 'email': 'nanaxn@wp.pl',
//                 'password': 'password',
//                 'sex': '1',
//                 'weight': '60',
//                 'height': '170',
//                 'allergies': '0',
//                 'calories_intake_daily': '1222',
//                 'weight_goal': '60',
//                 'user_name': 'lupinka',
//                 'birthDate': '4.12.1998'
//             };

//             axios.post('http://192.168.99.100:5678/user', user)
//                 .then((res) => {
//                     expect(res.status).toBe(201);
//                 })
//                 .catch((e) => {
//                     //console.log(e);
//                 });
//         });
//     });

    describe('GET User', function() {
         it('should get an user object', function(){
            axios.get('/user?id=4')
                .then((res) => {
                    console.log(res.status)
                    expect(res.status).toBe(200);
                })
                .catch((e) => {
                    console.log(e);
                });
        })
    })