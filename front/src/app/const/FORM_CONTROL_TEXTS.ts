export const formControlTextsConstants = {
  // email
  EMAIL_REQUIRED: 'L\'adresse e-mail est requise.',
  EMAIL_INVALID: 'L\'adresse e-mail est invalide.',

  // username
  USERNAME_LENGTH_MIN: 'Le nom d\'utilisateur doit comporter au moins 3 caractères.',
  USERNAME_REQUIRED: 'Le nom d\'utilisateur est requis.',

  // password
  PASSWORD_REQUIRED: 'Le mot de passe est requis.',
  PASSWORD_LENGTH_MIN: 'Le mot de passe doit comporter au moins 8 caractères.',
  PASSWORD_PATTERN: 'Le mot de passe doit contenir au moins un chiffre, une lettre majuscule, une lettre minuscule et un caractère spécial.',

  // title
  TITLE_REQUIRED: 'Le titre est requis.',
  TITLE_LENGTH_MIN: 'Le titre doit comporter au moins 3 caractères.',
  TITLE_LENGTH_MAX: 'Le titre doit comporter au maximum 20 caractères.',

  // content
  CONTENT_REQUIRED: 'Le contenu est requis.',
  CONTENT_LENGTH_MIN: 'Le contenu doit comporter au moins 3 caractères.',
  CONTENT_LENGTH_MAX: 'Le contenu doit comporter au maximum 255 caractères.',

  // theme
  THEME_REQUIRED: 'Le thème est requis.',

} as const;
