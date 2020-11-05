var mysql = require('mysql')

var connection = mysql.createConnection({
  // host: 'remotemysql.com',
  // user: 'vXuJMPGUOR',
  // password: '1qA6DALdlN',
  // database: 'vXuJMPGUOR'

  // host: 'localhost',
  // user: 'root',
  // password: '',
  // database: 'manga'

  host: 'remotemysql.com',
  user: 'fDedgt88ng',
  password: 'fJD03QiIWF',
  database: 'fDedgt88ng'
})

connection.connect(function (err) {
  if (err) {
    console.log(err)
  }
})

module.exports = connection
