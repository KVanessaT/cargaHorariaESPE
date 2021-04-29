export const environment = {
  url: 'http://localhost:8089/sch/',
  //url: 'https://servicios.espe.edu.ec:8443/carga_horaria-0.0.1-SNAPSHOT/sch/',
  production: false,
  sso: {
    serverUrl: 'https://srvcas.espe.edu.ec',
    clientId: 'F_13VTNPdVHPSstUZtYmldfl2UYa',
    // clientId: 'puy_fq2xdzPp2HvioN3p2986KoEa',
    //clientId: 'm8EnES0yApj2xQyVljatfkG0tyAa',
    issuer: '/oauth2endpoints/token',
    redirectUri: window.location.origin,
    postredirectUrL: window.location.origin,
    scope: 'openid profile email',
    logout: '/oidc/logout',
    tokenEndpoint: '/oauth2endpoints/token',
    userinfoEndpoint: '/oauth2/userinfo',
    authorizationEndpoint: '/oauth2/authorize',
    jwksEndpoint: '/oauth2/jwks',
    showDebugInformation: true,
    requireHttps: false,
    responseType: 'id_token token'
  }
};