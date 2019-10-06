var express = require('express')
var router = express.Router()
var database = require('../controllers/database')
var fs = require('fs')

router.post('/get_books', function (req, res) {
  database.getBooks((err, books) => {
    console.log(err)
    if (err) {
      res.json({ success: 'false' })
    } else {
      res.json({ success: 'true', books: books })
    }
  })
})

module.exports = router
