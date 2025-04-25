function main() {
  // Initialize all the plant details
  let name = document.querySelector("#name");
  name.textContent = plant_ffi.getName();
}

main();
