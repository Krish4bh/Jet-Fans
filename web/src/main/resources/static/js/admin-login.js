const form = document.querySelector("#login-form");
const loginEmailInput = document.querySelector("#lg-email-input");
const loginPassword = document.querySelector("#lg-password-input");
const lgEmailErrorMsg = document.querySelector("#lg-email-error");
const lgPasswordErrorMsg = document.querySelector("#lg-password-error");

form.addEventListener("submit", (e) => {
  lgEmailErrorMsg.classList.add("d-none");
  lgPasswordErrorMsg.classList.add("d-none");

  const email = loginEmailInput.value.trim();
  const password = loginPassword.value.trim();

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  const passRegex =
    /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,16}$/;

  let isValid = true;

  if (!emailRegex.test(email)) {
    lgEmailErrorMsg.textContent = "Enter a valid email";
    lgEmailErrorMsg.classList.remove("d-none");
    isValid = false;
  }

  if (!passRegex.test(password)) {
    lgPasswordErrorMsg.textContent =
      "Password (8 - 16) must contain 1 uppercase, 1 lowercase, 1 digit, 1 special character.";
    lgPasswordErrorMsg.classList.remove("d-none");
    isValid = false;
  }

  if (!isValid) {
    e.preventDefault();
  }
});

// Login input event

loginEmailInput.addEventListener("input", () => {
  const email = loginEmailInput.value.trim();

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  if (emailRegex.test(email)) {
    lgEmailErrorMsg.textContent = "";
    lgEmailErrorMsg.classList.add("d-none");
  } else {
    lgEmailErrorMsg.textContent = "Enter a valid email";
    lgEmailErrorMsg.classList.remove("d-none");
  }
});

loginPassword.addEventListener("input", () => {
  const password = loginPassword.value.trim();

  const passRegex =
    /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,16}$/;

  if (passRegex.test(password)) {
    lgPasswordErrorMsg.textContent = "";
    lgPasswordErrorMsg.classList.add("d-none");
  }
});
