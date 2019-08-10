var express = require('express')
var router = express.Router()
var database = require('../controllers/database')
var fs = require('fs')

router.get('/get', function (req, res) {
  database.getMangas((err, mangas) => {
    console.log(mangas)
    if (err) {
      res.json({ success: 'false' })
    } else {
      res.json({ success: 'true', mangas: mangas })
    }
  })
})

router.post('/getChapters', function (req, res) {
  database.getChaptersByMangaId(req.body.manga_id, (err, chapters) => {
    console.log(chapters)
    if (err) {
      res.json({ success: 'false' })
    } else {
      res.json({ success: 'true', chapters: chapters })
    }
  })
})

module.exports = router
