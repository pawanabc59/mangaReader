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

function setProfilePic (url, cb) {
  var sql = 'UPDATE `users` SET `profile_picture` = "' + url + '"'
  conn.query(sql, function (err, result) {
    cb(result)
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
  var sql = 'select *from manga order by title'
  conn.query(sql, function (err, result) {
    cb(err, result)
  })
}

function getChaptersByMangaId (manga_id, cb) {
  var sql = 'select *from chapters where manga_id = ' + manga_id + ' order by chapter_no'
  conn.query(sql, function (err, result) {
    cb(err, result)
  })
}

function getChaptersByMangaIdDesending (manga_id, cb) {
  var sql = 'select *from chapters where manga_id = ' + manga_id + ' order by chapter_no desc'
  conn.query(sql, function (err, result) {
    cb(result)
  })
}

module.exports = { getUser, newUser, setProfilePic, getMangas, getChaptersByMangaId }
