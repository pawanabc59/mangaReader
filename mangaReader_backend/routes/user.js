var express = require('express')
var app = express()
var router = express.Router()
var database = require('../controllers/database')
var fs = require('fs')
const mkdirp = require('mkdirp');
const session = require('express-session');
const multer = require('multer');
// var bodyParser = require('body-parser')


// app.use(session({secret:'noneed', resave: false, saveUninitialized: true}));

// by using locals we can access the session variable anywhere in the templates.
// app.use(function(req, res, next){
//   res.locals.email_id = req.session.email_id;
//   res.locals.status = "400";
//   next();
// });


// const storage = multer.diskStorage({
//   destination : function(req,file,cb){
//     console.log("inside storage");
//     const dir = './public/profile/'+ req.session.email_id;
//     mkdirp(dir, err => cb(null, dir));
//   },
//   filename : function(req,file,cb){
//     let temp = file.originalname;
//     cb(null , temp)
//   }
// });

// var upload = multer({ storage: storage })

router.post('/login', function (req, res) {
  // req.session.username = req.body.email_id;
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
  // fs.writeFile('public/profile/' + req.body.email_id + '.jpeg', req.body.profile_picture)

// send email_id also that where to set the photo
// console.log("profile_picture : "+req.body.profile_picture);
// console.log("email in set profile : "+req.body.email_id)

  mkdirp('./public/profile/'+req.body.email_id);
  fs.writeFile("./public/profile/"+req.body.email_id+'/'+req.body.email_id+'.jpeg', req.body.profile_picture, {encoding: 'base64'}, function(err){
    
    database.setProfilePic('/profile/'+req.body.email_id+'/'+req.body.email_id + '.jpeg', req.body.email_id, (err, result) => {
      console.log('image uploaded')
    res.json({ success: 'true', profile_picture: '/profile/' +req.body.email_id+'/'+req.body.email_id + '.jpeg', result: result })
  })
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

router.post('/favourite_book/add', (req, res) => {
  var values = [req.body.email_id, req.body.book_id]
  database.addFavouriteBook(values, (err, result) => {
    if (err) {
      res.json({ success: 'false' })
    } else {
      res.json({ success: 'true' })
    }
  })
})

router.post('/favourite_book/remove', (req, res) => {
  database.removeFavouriteBook(req.body.email_id, req.body.book_id, (err, result) => {
    if (err) {
      res.json({ success: 'false' })
    } else {
      res.json({ success: 'true' })
    }
  })
})

router.post('/favourites_book', (req, res) => {
  database.getFavouriteBook(req.body.email_id, (err, favourite_books) => {
    if (err) {
      res.json({ success: 'false' })
    } else {
      res.json({ success: 'true', favourite_books: favourite_books })
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

router.post('/chapter/rating', (req, res) => {
  database.setRating(req.body.email_id,req.body.chapter_id,req.body.rating, (err, result) => {
    if (err) {
      res.json({ success: 'false',err:err })
    } else {
      res.json({ success: 'true', result: result })
    }
  })
})

router.post('/get/chapter/rating', (req, res) => {
  database.getRating(req.body.email_id,req.body.chapter_id, (err, rating) => {
    if (err) {
      res.json({ success: 'false' ,err:err })
    } else {
      res.json({ success: 'true', rating: rating })
    }
  })
})

router.post('/book/rating', (req, res) => {
  database.setRatingBook(req.body.email_id,req.body.book_id,req.body.rating, (err, result) => {
    if (err) {
      res.json({ success: 'false',err:err })
    } else {
      res.json({ success: 'true', result: result })
    }
  })
})

router.post('/get/book/rating', (req, res) => {
  database.getRatingBook(req.body.email_id,req.body.book_id, (err, rating) => {
    if (err) {
      res.json({ success: 'false' ,err:err })
    } else {
      res.json({ success: 'true', rating: rating })
    }
  })
})


router.get('/index', function (req, res) {
  res.sendFile('', { root: './views' })
})

module.exports = router