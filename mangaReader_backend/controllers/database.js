var conn = require('../config/database-connection')

function getUser (email_id, password, cb) {
  var sql = 'select * from users where email_id ="' + email_id + '" and password ="' + password + '"'
  conn.query(sql, function (err, user) {
    cb(err, user[0])
  })
}

function newUser (values, cb) {
  var sql = 'INSERT INTO `users`(`email_id`, `password`) VALUES (?)'
  conn.query(sql, [values], function (err, result) {
    cb(err, result)
  })
}

function setProfilePic (url,email_id, cb) {
  var sql = 'UPDATE `users` SET `profile_picture`="'+url+'" WHERE email_id = "'+email_id+'"' 
  conn.query(sql, function (err, result) {
    var sql2  = 'select * from the users where email_id = "'+email_id+'"'
    conn.query(sql2, function(err, result1){
      cb(result1)
    })
    
  })
}

function getMangasByLastRelease (values, cb) {
  var sql = 'select *from manga order by last_release'
  conn.query(sql, function (err, result) {
    cb(result)
  })
}

function getMangasByRatings (values, cb) {
  var sql = 'select *from manga order by ratings desc'
  conn.query(sql, function (err, result) {
    cb(result)
  })
}

function getMangasByChapterCount (values, cb) {
  var sql = 'select *from manga order by number_of_chapters desc'
  conn.query(sql, function (err, result) {
    cb(result)
  })
}

function getMangasByMostRatings (values, cb) {
  var sql = 'select *from manga order by ratings_count desc'
  conn.query(sql, function (err, result) {
    cb(result)
  })
}

function getMangasByOriginDate (values, cb) {
  var sql = 'select *from manga order by date_of_origin desc'
  conn.query(sql, function (err, result) {
    cb(result)
  })
}

function getMangas (cb) {
  var sql = 'select * from manga order by title'
  conn.query(sql, function (err, result) {
    cb(err, result)
  })
}

function getBooks (email_id,cb) {
  // var sql = 'select * from book order by book_title'
  var sql = 'SELECT book.*,IF(fav.email IS NULL, "FALSE", "TRUE") as favourite_book\
              FROM book\
             LEFT JOIN (SELECT *from book_favourites where email = "' + email_id + '")\
             as fav\
             ON book.book_id = fav.book_id\
             order by book_title'
  conn.query(sql, function (err, result) {
    cb(err, result)
  })
}

function getHomeFeed (email_id, cb) {
  // hot_updates
  var sql = 'SELECT manga.*,IF(fav.email_id IS NULL, "FALSE", "TRUE") as favourite\
             FROM manga\
             LEFT JOIN (SELECT *from favourites where email_id = "' + email_id + '")\
             as fav\
             ON manga.manga_id = fav.manga_id\
             order by ratings desc'
  conn.query(sql, function (err, hot_updates) {
    // recommended
    var sql = 'SELECT manga.*,IF(fav.email_id IS NULL, "FALSE", "TRUE") as favourite\
              FROM manga\
             LEFT JOIN (SELECT *from favourites where email_id = "' + email_id + '")\
             as fav\
             ON manga.manga_id = fav.manga_id\
             order by title'
    conn.query(sql, function (err, recommended) {
      // popular
      var sql = 'SELECT manga.*,IF(fav.email_id IS NULL, "FALSE", "TRUE") as favourite\
             FROM manga\
             LEFT JOIN (SELECT *from favourites where email_id = "' + email_id + '")\
             as fav\
             ON manga.manga_id = fav.manga_id\
             order by ratings desc'
      conn.query(sql, function (err, popular) {
        // quick_reads
        var sql = 'SELECT manga.*,IF(fav.email_id IS NULL, "FALSE", "TRUE") as favourite\
             FROM manga\
             LEFT JOIN (SELECT *from favourites where email_id = "' + email_id + '")\
             as fav\
             ON manga.manga_id = fav.manga_id\
             order by number_of_chapters'
        conn.query(sql, function (err, quick_reads) {
          cb(err, hot_updates, recommended, popular, quick_reads)
        })
      })
    })
  })
}

function getChaptersByMangaId (manga_id, email_id, cb) {
  var sql = 'select *from chapters where manga_id = ' + manga_id + ' order by chapter_no'
  conn.query(sql, function (err, result) {
    var values = [email_id, manga_id]
    var sql = 'INSERT INTO `recent`(`email_id`, `manga_id`) VALUES (?)'
    conn.query(sql, [values], function (err, updateRecent) {
      cb(err, result)
    })
  })
}

function getChaptersByMangaIdDesending (manga_id, cb) {
  var sql = 'select *from chapters where manga_id = ' + manga_id + ' order by chapter_no desc'
  conn.query(sql, function (err, result) {
    cb(result)
  })
}

function addFavrourite (values, cb) {
  var sql = 'INSERT INTO `favourites`(`email_id`, `manga_id`) VALUES(?)'
  conn.query(sql, [values], function (err, result) {
    cb(err, result)
  })
}

function removeFavrourite (email_id, manga_id, cb) {
  var sql = 'DELETE FROM `favourites` WHERE email_id = "' + email_id + '" and manga_id = "' + manga_id + ' "'
  conn.query(sql, function (err, result) {
    cb(err, result)
  })
}

function getFavrourite (email_id, cb) {
  var sql = 'SELECT * from favourites,manga where favourites.manga_id = manga.manga_id and favourites.email_id = "' + email_id + '"'
  conn.query(sql, function (err, result) {
    cb(err, result)
  })
}

function addFavouriteBook (values, cb) {
  var sql = 'INSERT INTO `book_favourites`(`email`, `book_id`) VALUES(?)'
  conn.query(sql, [values], function (err, result) {
    cb(err, result)
  })
}

function removeFavouriteBook (email_id, book_id, cb) {
  var sql = 'DELETE FROM `book_favourites` WHERE email = "' + email_id + '" and book_id = "' + book_id + ' "'
  conn.query(sql, function (err, result) {
    cb(err, result)
  })
}

function getFavouriteBook (email_id, cb) {
  var sql = 'SELECT * from book_favourites,book where book_favourites.book_id = book.book_id and book_favourites.email = "' + email_id + '"'
  conn.query(sql, function (err, result) {
    cb(err, result)
  })
}

function getRecent (email_id, cb) {
  var sql = 'SELECT DISTINCT  * from recent,manga where recent.manga_id = manga.manga_id and recent.email_id = "' + email_id + '" order by at_time desc'
  conn.query(sql, function (err, recent) {
    cb(err, recent)
  })
}

function addRecent (email_id, cb) {

}
module.exports = { getRecent, getHomeFeed, getUser, newUser, setProfilePic, getMangas, getChaptersByMangaId, addFavrourite, removeFavrourite, getFavrourite, getBooks, addFavouriteBook, removeFavouriteBook, getFavouriteBook }
