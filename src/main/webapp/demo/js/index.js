/**
 * 
 */

var global = {},
	obj = {};

var $form = $('form'),
	$addBtn = $('.submit-btn'),
	$showBtn = $('.show-btn'),
	$modifyBtn = $('.modify-btn'),
	$ageBtn = $('.age-btn');

var elements = $form[0].elements;

var fields = ["name", "password", "secPassword", "age", "gender", "height", "description"];

var field,
	i,
	data,
	key;

/*
 * 需要输入校验 
 */

global.key = "key" + 1 * new Date();
global.ids = [];

$addBtn.on("click", function(e){

	if(!elements[1].value ||elements[1].value != elements[2].value){
		alert("密码不相同");
		return;
	}
	
	for(i = 0; i < fields.length; i++){
		elements[i].value && (obj[fields[i]] = elements[i].value);
	}
	
	console.log(obj);
	console.log(elements);


	$.ajax({
		url: '/demo/demo/save',
		type: 'post',
		dataType : "json",
		data: obj
	}).done(function(res, textStatus, jqXMR){
		obj = {};
		console.log(res);
	});
});

$showBtn.on("click", function(e){

	$.ajax({
		url: '/demo/demo/list',
		type: 'get',
		dataType : "json"
	}).done(function(res, textStatus, jqXMR){		
		console.log(res);
		if(!res.data.length){
			alert("没有数据");
			return;
		}

		data = res.data[0];

		for(field in data){
			obj[field] = data[field];
		}
		
		global.ids.push(obj.id);
		
		global[obj.id] = obj;
		for(i = 0; i < fields.length; i++){
			elements[i].value = obj[fields[i]];
		}
		
		console.log(global);
	});
});

$modifyBtn.on("click", function(e){

	if(!elements[1].value ||elements[1].value != elements[2].value){
		alert("密码不相同");
		return;
	}
	
	for(i = 0; i < fields.length; i++){
		obj[fields[i]] = elements[i].value;
	}
	
	$.ajax({
		url: '/',
		type: 'post',
		dataType : "json",
		data: {
			id:global.data[-1]
		}
	}).done(function(res, textStatus, jqXMR){
		global[global.ids[-1]] = obj;
		obj = {};
		console.log(res);
	});
});


$ageBtn.on('click', function(e){
	var val = document.querySelector('.age input').value || 1;

	console.log(val);

	$.ajax({
		url: '/demo/demo/age',
		type: 'post',
		dataType : "json",
		data: {
			age: val
		}
	}).done(function(res, textStatus, jqXMR){
		console.log(res);
	});



})