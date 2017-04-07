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