const models = require('../../models/models');

exports.index = (req, res) => {

    models.Dist.findAll().then(function(results) {
        res.json(results);
    }).catch(function(err) {
        //TODO: error handling
        return res.status(404).json({err: 'Undefined error!'});
    });
};


exports.show = (req, res) => {
    // console.log("show");
    const id = parseInt(req.params.id, 10);
    if(!id){
        return res.status(400).json({err: 'Incorrect id'});
    }

    models.Dist.findOne({
        where: {
            id: id
        }
    }).then(dist => {
        if(!dist){
            return res.status(404).json({err: 'No dist'});
        }
        return res.json(dist);
    });
};

exports.destroy = (req, res) => {
    // console.log("destory");
    const id = parseInt(req.params.id, 10);
    if (!id) {
        return res.status(400).json({error: 'Incorrect id'});
    }

    models.Dist.destroy({
        where: {
            id: id
        }
    }).then(() => res.status(204).send());
};

exports.create = (req, res) => {
    // console.log("create");
    const mst = req.body.mst || '';
    console.log(mst)

    if(!mst.length){
        return res.status(400).json({err: 'Incorrect dist info'});
    }
    
    models.Dist.create({
        mst: mst,
    }).then((dist) => res.status(201).json(dist));
};

exports.update = (req, res) => {
    const newName = req.body.mst || '';
    const mst = models.Dist.mst;
    const id = parseInt(req.params.id, 10);

    if(!mst.length){
        return res.status(400).json({err: 'Incrrect mst'});
    }

    models.Dist.update(
        {mst: newName},
        {where: {id: id}, returning: true})
        .then(function(result) {
             res.json(result[1][0]);
        }).catch(function(err) {
             //TODO: error handling
             return res.status(404).json({err: 'Undefined error!'});
    });
}