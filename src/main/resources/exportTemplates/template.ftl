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

        #intro {
            position: relative;
        }

        #intro img {
            margin-top: 20px;
            width: 175px;
            height: 175px;
        }

        #careerLevel {
        }

        #maredit-address {
            display: table-cell;
            vertical-align: bottom;
            color: #A6A6A6;
        }

        .block {
            width: 100%;
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

        .text-left {
            text-align: left;
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
            font-size: 12px;

        }

        h1 {
            font-size: 42px;
        }

        hr {
            margin-top: 30px;
            margin-bottom: 30px;
            border: 0;
            height: 1px;
            background: #333 linear-gradient(to right, #ccc, #333, #ccc);
        }

        .header {
            margin-top: 10px;
            margin-bottom: 50px;
            text-transform: uppercase;
            font-size: 14px;
            position: running(header);
        }

        .footer {
            margin-bottom: 10px;
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
            margin: 25mm 25mm 25mm 25mm;

            @top-left {
                content: element(header);
            }

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
        <img class="left" src="defaultProfileImage.png" alt="ffs"/>

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

    <#if (firstname)?has_content>
        <p>
            <label>Name:</label>
            <span>${firstname}</span>
        </p>
    </#if>
    <#if (lastname)?has_content>
        <p>
            <label>Vorname: </label>
            <span>${lastname}</span>
        </p>
    </#if>
    <#if (degrees)?has_content>
        <p>
            <label>Abschluss: </label>
            <span>${degrees}</span>
        </p>
    </#if>
    <#if (careerLevel)?has_content>
        <p>
            <label>Karrierestufe: </label>
            <span>${careerLevel}</span>
        </p>
    </#if>
    <#if (workExperience)?has_content>
        <p>
            <label>Berufserfahrung seit: </label>
            <span>
            ${workExperience}
            </span>
        </p>
    </#if>
    <#if (languages)?has_content>
        <p>
            <label>Fremdsprachen: </label>
            <span>${languages}</span>
        </p>
    </#if>
    </div>
    <div id="experiences" class="block left green">
        <h3>Erfahrungen.</h3>
    <#if industries?has_content>
        <p>
            <label>Branchen:</label>
            <span>${industries}</span>
        </p>
    </#if>
    </div>
    <div id="knowledge" class="block left orange">
        <h3>Kenntnisse.</h3>
    <#if industries?has_content>

        <p>
            <label>Platformen:</label>
            <span>${platforms}</span>
        </p>
    </#if>
    <#if platforms?has_content>
        <p>
            <label>Betriebssysteme:</label>
            <span>${platforms}</span>
        </p>
    </#if>
    <#if progLanguages?has_content>
        <p>
            <label>Programmiersprachen:</label>
            <span>${progLanguages}</span>
        </p>
    </#if>
    <#if webTechnologies?has_content>
        <p>
            <label>Web-Technologien:</label>
            <span>${webTechnologies}</span>
        </p>
    </#if>
    <#if devEnvironments?has_content>
        <p>
            <label>Entwicklungswerkzeuge:</label>
            <span>${devEnvironments}</span>
        </p>
    </#if>
    <#if qualifications?has_content>
        <p>
            <label>Qualifikationen</label>
            <span>${qualifications}</span>
        </p>
    </#if>
        <hr/>
    </div>
    <div id="summary" class="block">
        <h3>Zusammenfassung</h3>
    <#if summary?has_content>
    ${summary}
    </#if>
    </div>
<#list projectAssociations as projectAssociation>
    <div class="page-break"></div>
    <div class="block left purple project">
        <h3>KLIENT.</h3>
        <#if projectAssociation.project?has_content &&
        projectAssociation.project.companies?has_content>
            <p>
                <label>Firma:</label>
                <span>${projectAssociation.project.companies}</span>
            </p>
        </#if>
        <#if (projectAssociation.project)?has_content && projectAssociation.project.locations?has_content>
            <p>
                <label>Standort:</label>
                <span>${projectAssociation.project.locations}</span>
            </p>
        </#if>
        <#if (projectAssociation.project)?has_content && projectAssociation.project.industries?has_content>
            <p>
                <label>Branche:</label>
                <span>${projectAssociation.project.industries}</span>
            </p>
        </#if>
        <#if (projectAssociation.project)?has_content && projectAssociation.project.title>
            <p>
                <label>Projekttitel: </label>
                <span>${projectAssociation.project.title}</span>
            </p>
        </#if>
        <#if projectAssociation.locations?has_content>
            <p>
                <label>Einsatzorte:</label>
                <span>${projectAssociation.locations}</span>
            </p>
        </#if>
        <#if projectAssociation.start?has_content || projectAssociation.end?has_content>
            <p>
                <label>Zeitraum:</label>
            <span><#if projectAssociation.start?has_content>${projectAssociation.start}</#if> <#if projectAssociation.end?has_content>
                - ${projectAssociation.end}</#if></span>
            </p>
        </#if>
        <hr/>
        <div style="margin-bottom: 30px"></div>
    </div>
    <div class="block">
        <#if (projectAssociation.project)?has_content && projectAssociation.project.summary?has_content>
            <h3>Projektbeschreibung:</h3>

            <p>${projectAssociation.project.summary}</p>
        </#if>
        <#if projectAssociation.positions?has_content && projectAssociation.positions.size() > 0>
            <h3>Position:</h3>
            <ul>
                <#list projectAssociation.positions as position>
                    <li>${position}</li>
                </#list>
            </ul>
        </#if>
        <#if projectAssociation.tasks?has_content>
            <h3>Aufgaben:</h3>

            <p>${projectAssociation.tasks}</p>
        </#if>
        <#if projectAssociation.technologies?has_content && projectAssociation.technologies.size() > 0>
            <h3>Technologien:</h3>
            <ul>
                <#list projectAssociation.technologies as technology>
                    <li>${technology}</li>
                </#list>
            </ul>
        </#if>
    </div>
</#list>
</div>

</body>
</html>