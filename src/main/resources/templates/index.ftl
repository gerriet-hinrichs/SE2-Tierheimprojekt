<#--
 * Application to help putting pets from animal shelter across.
 * Copyright (C) 2017
 *     Felix Koch <felix.koch@haw-hamburg.de>,
 *     Kristian Exss <Kristian.Exss@HAW-Hamburg.de>,
 *     Adrian Bostelmann <Adrian.Bostelmann@HAW-Hamburg.de>,
 *     Karsten Boehringer <Karsten.Boehringer@HAW-Hamburg.de>,
 *     Gehui Xu <Gehui.Xu@HAW-Hamburg.de>,
 *     Gerriet Hinrichs <gerriet.hinrichs@haw-hamburg.de>.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
-->

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
    <p><input type="text" value="{{textInput}}"/>
        <button click="{{sentText}}">Send</button>
    </p>
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