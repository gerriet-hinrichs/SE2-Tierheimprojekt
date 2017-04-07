<#-- @ftlvariable name="model" type="de.stuff42.se2tierheimprojekt.model.AppStartModel" -->
<!Doctype html>
<html>
<head>
    <title>SE 2 Tierheimprojekt</title>
    <link rel="stylesheet" type="text/css" href="static/app.css">
</head>
<body>
<div class="box">
    <p>Spring works!</p>
    <p id="requireWorks">RequireJS does not work.</p>
    <p text="{{knockoutWorkingMessage}}">Knockout does not work.</p>
</div>
<div class="box">
    {{#foreach itemList}}
    <p>{{id}}: {{name}} ({{other.id}}: {{other.name}})</p>
    {{/foreach}}
    <p><input type="text" value="{{textInput}}" /><button click="{{sentText}}">Send</button></p>
</div>
<script type="application/javascript">
    window["BuildVersion"] = ${model.buildVersionJS};
    window["BuildTime"] = ${model.buildTimeJS};
    window["DEBUG"] = ${model.debugJS};
</script>
<script type="application/javascript"
        src="http://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.8/require<#if !model.isDebug>.min</#if>.js"
        data-main="static/view/config"></script>
</body>
</html>