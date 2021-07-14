console.log("this is script file");
let toggleNavStatus = false;
const toggleSidebar=()=>{
 
 let sidebar = document.querySelector(".sidebar"); 
 let content = document.querySelector(".content");
 
 
if(toggleNavStatus){
	 sidebar.style.display= "block";
	 content.style.marginLeft="20%";
   	 toggleNavStatus=false;
}else{
	 sidebar.style.display= "none";
	 content.style.marginLeft="15px";
     toggleNavStatus=true;
}

};


const deleteContact =(cid)=>{
	swal({
  title: "Are you sure?",
  text: "Once deleted, you will not be able to recover this imaginary file!",
  icon: "warning",
  buttons: true,
  dangerMode: true,
})
.then((willDelete) => {
  if (willDelete) {
	
	window.location="/user/delete/"+cid;
   
    swal("Poof! Your imaginary file has been deleted!", {
      icon: "success",
    });
  } else {
    swal("Your imaginary file is safe!");
  }
});
}