var mysql = require('mysql')

var connection = mysql.createConnection({
  host: 'remotemysql.com',
  user: 'XFE1iAH6p1',
  password: 'QyI1GVn79q',
  database: 'XFE1iAH6p1'
  // host: 'localhost',
  // user: 'root',
  // password: '',
  // database: 'manga'
})

connection.connect(function (err) {
  if (err) {
    console.log(err)
  }
})

module.exports = connection
