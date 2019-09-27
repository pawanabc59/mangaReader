var express = require('express')
var router = express.Router()
var database = require('../controllers/database')
var fs = require('fs')

router.post('/login', function (req, res) {
  database.getUser(req.body.email_id, req.body.password, (err, user) => {
    if (user) {
      console.log(user)
      res.json({ success: 'true', user: user })
    } else {
      res.json({ success: 'false' })
    }
  })
})

router.post('/register', (req, res) => {
  var values = [req.body.email_id, req.body.password]
  console.log(values)
  database.newUser(values, (err, result) => {
    console.log(result)
    if (err) {
      res.json({ success: 'false' })
    } else {
      res.json({ success: 'true' })
    }
  })
})

router.post('/setProfilePic', (req, res) => {
  console.log(req.body.email_id)
  // var writer = fs.createWriteStream('../public/profile/' + req.body.email_id + '.jpeg', { flag: 'wx' })
  fs.writeFile('public/profile/' + req.body.email_id + '.jpeg', req.body.profile_picture)

  database.setProfilePic('/profile/' + req.body.email_id + '.jpeg', (err, result) => {
    res.json({ success: 'true', profile_picture: '/profile/' + req.body.email_id + '.jpeg' })
  })
})

router.post('/favourite/add', (req, res) => {
  var values = [req.body.email_id, req.body.manga_id]
  database.addFavrourite(values, (err, result) => {
    if (err) {
      res.json({ success: 'false' })
    } else {
      res.json({ success: 'true' })
    }
  })
})

router.post('/favourite/remove', (req, res) => {
  database.removeFavrourite(req.body.email_id, req.body.manga_id, (err, result) => {
    if (err) {
      res.json({ success: 'false' })
    } else {
      res.json({ success: 'true' })
    }
  })
})

router.post('/favourites', (req, res) => {
  database.getFavrourite(req.body.email_id, (err, favourites) => {
    if (err) {
      res.json({ success: 'false' })
    } else {
      res.json({ success: 'true', favourites: favourites })
    }
  })
})

router.post('/home/feed', function (req, res) {
  database.getHomeFeed(req.body.email_id, (err, hot_updates, recommended, popular, quick_reads) => {
    console.log(req.body.email_id)
    if (err) {
      res.json({ success: 'false' })
    } else {
      res.json({ success: 'true', hot_updates: hot_updates, recommended: recommended, popular: popular, quick_reads: quick_reads })
    }
  })
})

router.post('/recent', (req, res) => {
  database.getRecent(req.body.email_id, (err, recents) => {
    if (err) {
      res.json({ success: 'false' })
    } else {
      res.json({ success: 'true', recents: recents })
    }
  })
})

router.get('/index', function (req, res) {
  res.sendFile('', { root: './views' })
})

module.exports = router
