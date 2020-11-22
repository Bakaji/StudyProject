<template>
  <div class="container">
    <h1>Modifier Utilisateur</h1>
    <div class="form-container">
      <form method="POST" :action="postLink">
        <label for="username">Nom d'utilisateur</label>
        <input id="username" required type="text" name="username">
        <label for="password">Mot de passe</label>
        <input id="password" required type="password" name="password">
        <label for="conf_password">Confirmation mot de passe</label>
        <input id="conf_password" required type="password" name="conf_password">
        <div>
          <input type="submit" value="Modifier">
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import {relativeLink} from "@/main";

export default {
  name: "EditAdmin",
  computed: {
    postLink: function () {
      return relativeLink("/edit-admin");
    }
  }
}
</script>

<style lang="scss">
.container{

  .form-container{
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
    form{
      display: flex;
      flex-wrap: wrap;
      width: 80%;
      label{
        flex-basis: 40%;
        font-size: 18px;
        font-weight: bold;
      }
      input{
        flex-basis: 60%;
        height: 40px;
        margin-bottom: 15px;
      }
      div{
        flex-basis: 100%;
        direction: rtl;
        input{
          width: 200px;
          display: block;
        }
      }
    }
  }
}
</style>
