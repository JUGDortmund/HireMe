<#-- @ftlvariable name="" type="model.Profile" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>${firstname} ${lastname}</title>
    <link href='http://fonts.googleapis.com/css?family=Didact+Gothic' rel='stylesheet' type='text/css'/>
    <style type="text/css">
        #content {

        }

        #intro img {
            margin-top: 20px;
            width: 175px;
            height: 175px;
        }

        #careerLevel {
            margin-top: 0;
        }

        #maredit-address {
            color: #A6A6A6;
        }

        .block {
            margin-top: 20px;
            clear: left;
        }

        .block h3 {
            text-transform: uppercase;
        }

        .block label {
            display: inline-block;
            width: 175px;
            text-align: right;
            vertical-align: top;
        }

        .block span {
            display: inline-block;
            max-width: 450px;
            padding-left: 15px;
            color: black;
        }

        .left {
            float: left;
        }

        .right {
            float: right;
        }

        .text-right {
            text-align: right;
        }

        .blue {
            color: #3c8dbc;
        }

        .orange {
            color: #ff7701;
        }

        .green {
            color: #00a65a;
        }

        .purple {
            color: #8064a2;
        }

        body {
            font-family: 'Didact Gothic', sans-serif;
            font-size: 10px;
            margin-left: 30px;
            margin-right: 30px;
        }

        hr {
            margin-top: 10px;
            border: 0;
            height: 1px;
            background: #333 linear-gradient(to right, #ccc, #333, #ccc);
        }

        .header {
            margin-bottom: 50px;
            text-transform: uppercase;
            font-size: 14px;
            position: running(header);
        }

        .footer {
            position: running(footer);
        }

        .footer img {
            width: 175px;
            max-height: 100%;
        }

        @media print {
            .page-break {
                display: block;
                page-break-before: always;
            }
        }

        @page {
            @top-left {
                content: element(header);
            }
        }

        @page {
            @bottom-right {
                content: element(footer);
            }
        }
    </style>
</head>

<body>
<div class="header">
    <h2>Mitarbeiterprofil.</h2>
    _____
</div>
<div class="footer">
    <img class="right" src="maredit-logo.png" alt="maredit-logo"/>
</div>
<div id="content">
    <div id="intro">
        <img class="left" src="profileImage.png" alt="ffs"/>

        <div class="right text-right">
            <p id="careerLevel" class="blue">
            ${careerLevel}
            </p>

            <h1>
            ${firstname}<br/>
            ${lastname}
            </h1>

            <p id="maredit-address">maredit GmbH • Sebrathweg 20 • 44149 Dortmund • www.maredit.de</p>
        </div>
    </div>
    <div id="personal" class="block left blue">
        <h3>Person.</h3>

        <p>
            <label>Name:</label>
            <span>${firstname}</span>
        </p>

        <p>
            <label>Vorname: </label>
            <span>${lastname}</span>
        </p>

        <p>
            <label>Abschluss: </label>
            <span>${degrees}</span>
        </p>

        <p>
            <label>Karrierestufe: </label>
            <span>${careerLevel}</span>
        </p>

        <p>
            <label>Berufserfahrung seit: </label>
            <span>
            ${workExperience?string}
            </span>
        </p>

        <p>
            <label>Fremdsprachen: </label>
            <span>${languages}</span>
        </p>
    </div>
    <div id="experiences" class="block left green">
        <h3>Erfahrungen.</h3>

        <p>
            <label>Branchen:</label>
            <span>${industries}</span>
        </p>

    </div>
    <div id="knowledge" class="block left orange">
        <h3>Kenntnisse.</h3>

        <p>
            <label>Platformen:</label>
            <span>${platforms}</span>
        </p>

        <p>
            <label>Betriebssysteme:</label>
            <span>${platforms}</span>
        </p>

        <p>
            <label>Programmiersprachen:</label>
            <span>${platforms}</span>
        </p>

        <p>
            <label>Web-Technologien:</label>
            <span>${platforms}</span>
        </p>

        <p>
            <label>Entwicklungswerkzeuge:</label>
            <span>${platforms}</span>
        </p>

        <p>
            <label>Qualifikationen</label>
            <span>${platforms}</span>
        </p>
        <hr style="margin-top: 20px"/>
    </div>
    <div id="summary" class="block">
        <h3>Zusammenfassung</h3>
    ${summary}
    </div>
<#list projectAssociations as projectAssociation>
    <div class="page-break"></div>
    <div class="block left purple project">
        <h3>KLIENT.</h3>

        <p>
            <label>Firma:</label>
            <span>${projectAssociation.project.companies}</span>
        </p>

        <p>
            <label>Standort:</label>
            <span>${projectAssociation.project.locations}</span>
        </p>

        <p>
            <label>Branche:</label>
            <span>${projectAssociation.project.industries}</span>
        </p>

        <p>
            <label>Projekttitel: </label>
            <span>${projectAssociation.project.title}</span>
        </p>

        <p>
            <label>Einsatzorte:</label>
            <span>${projectAssociation.locations}</span>
        </p>

        <p>
            <label>Zeitraum:</label>
            <span>${projectAssociation.start} - ${projectAssociation.end}</span>
        </p>

        <hr style="margin-top: 20px"/>
    </div>
    <div class="block">
        <h3>Projektbeschreibung:</h3>

        <p>${projectAssociation.project.summary}</p>

        <h3>Position:</h3>
        <ul>
            <#list projectAssociation.positions as position>
                <li>${position}</li>
            </#list>
        </ul>
        <h3>Aufgaben:</h3>

        <p>${projectAssociation.tasks}</p>
        <h3>Technologien:</h3>
        <ul>
            <#list projectAssociation.technologies as technology>
                <li>${technology}</li>
            </#list>
        </ul>
    </div>
</#list>
</div>

</body>
</html>