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
	
	$sql = $base->prepare("SELECT * FROM uvhist ORDER BY id DESC LIMIT 2,1");
	$sql->execute();
	
	while($row[] = $sql->fetch(PDO::FETCH_ASSOC)) 
	{
       $json = json_encode($row);
    }
	
	echo $json;
?>