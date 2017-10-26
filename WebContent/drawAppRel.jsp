<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="../../dist/mermaid.css">
    
    <script src="../../dist/mermaid.js"></script>
</head><body>

<div class="mermaid">
 graph LR;
ESB*系统[客户评级 <br/>CRM<br/>核心系统]-->ESB*
ESB* -->信贷系统
AFE*系统[客户评级 <br/>CRM<br/>核心系统]-->AFE*
AFE* -->信贷系统
直连系统*[电话外呼系统<br/>网易贷] --> 信贷系统

信贷系统-->直连系统明细[网易贷 <br/>数据仓库<br/>信贷系统<br/>1104];
信贷系统 --> ESB
ESB-->ESB系统[客户评级 <br/>CRM<br/>核心系统]
信贷系统 --> AFE
AFE --> AFE系统[客户评级 <br/>CRM<br/>核心系统]
classDef red fill:#ff8080;
class 信贷系统 red;
</div>
</body></html>