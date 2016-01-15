var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res) {
    res.render('scheduling', { title: 'Online Clinic - Scheduling' });
});

module.exports = router;