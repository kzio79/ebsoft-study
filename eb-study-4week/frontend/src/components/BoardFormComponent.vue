<template>
  <form @submit.prevent="modifyContentList" enctype="multipart/form-data">
    <table style= "width:80%;">
      <tr>
        <td style="color: black; font-weight: bolder;">
          카테고리 {{categoryNames[viewContent.categoryNum]}}
        </td>
      </tr>
      <tr>
        <td style="color: black; font-weight: bolder;">
          등록일시 {{formatDate(viewContent.writeDate)}}
        </td>
      </tr>
      <tr>
        <td style="color: black; font-weight: bolder;">
          수정일시 {{formatDate(viewContent.modifyDate)}}
        </td>
      </tr>
      <tr>
        <td style="color: black; font-weight: bolder;">
          조회수&nbsp;&nbsp;&nbsp;&nbsp;{{viewContent.hit}}
        </td>
      </tr>
      <tr>
        <td style="color: black; font-weight: bolder;">
          작성자&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" v-model="viewContent.writer">
        </td>
      </tr>
      <tr>
        <td style="color: black; font-weight: bolder;">
          비밀번호&nbsp;<input type="password" v-model="viewContent.pw">
        </td>
      </tr>
      <tr>
        <td style="color: black; font-weight: bolder;">
          글제목&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" v-model="viewContent.title">
        </td>
      </tr>
      <tr>
        <td style="color: black; font-weight: bolder;">
          글내용<textarea rows="12" style="width:100%;" class="click" name="content" v-model="viewContent.content" />
        </td>
      </tr>

      <tr>
        <ul>
          <td style="color: black; font-weight: bolder">
            <br />
            등록된 파일
            <br />
            <tr v-for="(file, index) in viewListFile" :key="index">
              <td>
                <a :href="`${$serverUrl}downloadFile?boardNum=${boardNum}`" style="text-decoration: none; color: black">
                  {{ file.originalFileName}}<br />
                </a>
              </td>
            </tr>
          </td>
        </ul>
      </tr>

      <tr style="margin-bottom: 5%">
        <!-- 글 수정 메뉴 -->
        <td colspan="2" align="center" style="margin-bottom: 5%">
          <button @click.prevent="router.go(-1)">취소</button>
          <input type="submit" value="저장">
        </td>
      </tr>
    </table>
  </form>
</template>
<script setup>
import router from "@/router";
</script>