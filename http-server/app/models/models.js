const config = require('../config/environment');
const Sequelize = require('sequelize');

const sequelize = new Sequelize(
    config.mysql.database,
    config.mysql.username,
    config.mysql.password, {
        host: 'localhost',
        dialect: 'mysql'
    }
);
    
const Dist = sequelize.define('dist', {
    mst: Sequelize.STRING});
    
const User = sequelize.define('user', {
    userid: Sequelize.STRING,
    userpw: Sequelize.STRING});



module.exports = {
    sequelize: sequelize,
    User: User,
    Dist: Dist
}