/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

window.onload = function () {
    
    function apperer_disapperer(aparece, p01) {
        if (aparece[0]){
            aparece[0] = false;
            p01.style.display = 'none';
        } else {
            aparece[0] = true;
            p01.style.display = 'block';
        }
    }
    
    b02 = document.getElementById('aparecer_menu');
    b02.onclick = (function (){
        var aparece = [false];
        var p01 = document.getElementById('menu');
        return function () {
            apperer_disapperer(aparece, p01);
        }
    }());
      
    
    function scaleMedia(x) {
        var pMenu = document.getElementById('menu');
        
        if (x.matches) { // If media query matches
            pMenu.style.display = 'none';
        
        } else {
            pMenu.style.display = 'block';
        }
    }  
    
    var x = window.matchMedia("(max-width: 600px)")
    scaleMedia(x) // Call listener function at run time
    x.addListener(scaleMedia) 
}


