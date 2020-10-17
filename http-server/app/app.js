const express = require('express');
const bodyParser = require('body-parser');
const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));

app.use('/users', require('./routes/users/users'));
app.use('/dists', require('./routes/dists/dists'));

module.exports = app;