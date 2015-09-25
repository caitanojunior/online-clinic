var express = require('express');
var router = express.Router();

/* GET clients listing. */
router.get('/clientlist', function(req, res) {
    var db = req.db;
    var collection = db.get('clientlist');
    collection.findById({},{},function(e,docs){
        res.json(docs);
    });
});

/*
 * POST to addclient.
 */
router.post('/addclient', function(req, res) {
    var db = req.db;
    var collection = db.get('clientlist');
    collection.insert(req.body, function(err, result){
        res.send(
            (err === null) ? { msg: '' } : { msg: err }
        );
    });
});

/*
 * PUT to updateClient.
 */
router.put('/updateclient/:id', function(req, res){

    var db = req.db;
    var collection = db.get('clientlist');
    collection.findById(req.params.id,function(err,docs){
        res.json(docs);
        var clientToUpdate = req.params.id;
        collection.save({ '_id' : clientToUpdate }, function(err){
            res.send((err === null) ? { msg: '' } : { msg:'error: ' + err });
        });
    });
});


/*
 * DELETE to deleteClient.
 */
router.delete('/deleteclient/:id', function(req, res) {
    var db = req.db;
    var collection = db.get('clientlist');
    var clientToDelete = req.params.id;
    collection.remove({ '_id' : clientToDelete }, function(err) {
        res.send((err === null) ? { msg: '' } : { msg:'error: ' + err });
    });
});

module.exports = router;
