const express = require('express');
const app = express();
const port = 5101;

app.use(express.static('public'));

app.get('/', (req, res) => {
	res.send(`
			<div id="">div1</div>
			<div id="">div2</div>
			<div id="">div3</div>
	`);
})


app.post('/create', (req,res) => {
	console.log('create!');
}) 

app.get('/read', (req, res) => {
	console.log('read!');
})

app.post('/update', (req, res) => { 
	console.log('update!');
})

app.post('/delete', (req, res) => {
	console.log('delete!');
})

app.listen(port, () => {
	console.log(`Server listening at http://localhost:${port}`);
})
