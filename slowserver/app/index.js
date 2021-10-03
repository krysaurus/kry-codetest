const express = require("express");
const bodyParser = require("body-parser");
const cors = require('cors');
const app = express();
const port = 3000;

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.get('/', (req, res, next) => {
    setTimeout(() => res.send('🐢🐢🐢🐢🐢🐢🐢🐢🐢🐢🐢🐢'), 10000)
})

app.listen(port)

