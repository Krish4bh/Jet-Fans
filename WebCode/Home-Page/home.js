const dummyProducts = [
  { name: "Tower Fan" },
  { name: "Ceiling Fan" },
  { name: "Table Fan" },
];

const lgSearchBar = document.querySelector("#search-input");
const lgSearchResults = document.querySelector("#search-results");

lgSearchBar.addEventListener("input", () => {
  const searchInput = lgSearchBar.value.toLowerCase().trim();

  if (searchInput == "") {
    lgSearchResults.innerHTML = "";
    return;
  }

  const filteredProducts = dummyProducts.filter((product) =>
    product.name.toLowerCase().includes(searchInput)
  );

  searchRender(filteredProducts);
});

const searchRender = (filteredArray) => {
  lgSearchResults.innerHTML = "";

  if (filteredArray.length == 0) {
    const li = document.createElement("li");
    li.textContent = "No results found...";
    lgSearchResults.appendChild(li);
    return;
  }

  filteredArray.forEach((product) => {
    const li = document.createElement("li");
    li.textContent = product.name;
    lgSearchResults.appendChild(li);
  });
};
