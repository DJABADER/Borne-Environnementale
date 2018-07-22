<?php
	$hostmysql  = '127.0.0.1';//serveur de la base de donnée
	$usermysql  = 'root';//nom d'utilisateur de la BDD
	$passmysql  = '';//Mot de passe de la BDD
	$basemysql  = 'projet_borne';//Nom de la BDD
		
    try
    {
        $base = new PDO ("mysql:host=$hostmysql;port=3307;dbname=$basemysql",$usermysql,$passmysql);
    }
    catch (PDOException $event)
    {
        die('Erreur :'.$event->getMessage());
    }
	
    $UV = $_GET["UV"];
    $RAD = $_GET["RAD"];

	$uvINS = $base->prepare("INSERT INTO uvhist (Date,Heure,UV) VALUES (now(),now(),'$UV')");
	$uvINS->execute();

    $radINS = $base->prepare("INSERT INTO radhist (Date,Heure,Rad) VALUES (now(),now(),'$RAD')");
	$radINS->execute();
?>