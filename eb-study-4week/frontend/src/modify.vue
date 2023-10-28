<script setup>
import { computed, inject, onMounted, ref } from "vue";
import axios from "axios";
import router from "@/router";
import { useRoute } from "vue-router";
import apiService from "@/service/apiService";

// 전역변수 Url
const $serverUrl = inject('$serverUrl')

// data 속성 정의
const viewBoardContent = ref(null)
let viewListFile = []

let file = ref(null)
let file1 = ref(null)
let file2 = ref(null)

const categoryNames = ['카테고리', 'JAVA', 'JS', 'SpringBoot', 'Android']

const route = useRoute()
const boardNum = route.query.boardNum

//computed로 생성된 값은 그 값에 의존하는 다른 값이 변경될 때마다 자동으로 업데이트
let viewContent = computed(() => viewBoardContent.value ? viewBoardContent.value : {})


// const fileList = new Array();

/**
 * 게시판 content 불러오기
 * @returns {Promise<void>}
 */
const viewContentAndReply = async () => {
  try {

    const response = await apiService.getContent(boardNum)
    viewBoardContent.value = response.data.viewBoardContent
    viewListFile = response.data.viewListFile

  } catch (error) {
    console.log("댓글 전송 에러 : ", error.response || error.message)
  }
}

onMounted(async () => {
  await viewContentAndReply()
})

/**
 * 게시판 수정
 * @returns {Promise<void>}
 */
const modifyContentList = async () => {
  try {

    const response = await apiService.postModify(boardNum,viewContent)

    if (response.data && boardNum) {

        if((file.value && file.value.files[0]) || (file1.value && file1.value.files[0]) || (file2.value && file2.value.files[0])){

        const formData = new FormData();

        if (file.value && file.value.files[0]) {
          formData.append('files', file.value.files[0])
        }
        if (file1.value && file1.value.files[0]) {
          formData.append('files', file1.value.files[0])
        }
        if (file2.value && file2.value.files[0]) {
          formData.append('files', file2.value.files[0])
        }

        formData.append('boardNum', boardNum)

        await apiService.postFile(formData)
      }
      alert("게시물이 수정 되었습니다.")
      await router.push({ name: 'content', query: { boardNum: boardNum } })
    } else {
      console.error("게시물 수정에 실패했습니다.")
    }

    router.push({ name: 'content', query: {boardNum : boardNum} })

  } catch (error) {
    console.error('전송 에러:' ,error.response || error.message)
  }
}

const formatDate = (dataString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' }
  return new Date(dataString).toLocaleDateString(undefined, options)
}

</script>


<template>
  <div>
  <h1>게시판 - 수정</h1>
  <br/>
  <div style="margin-top: 1%; align-content: center; align-items: center; text-align: center">
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
  </div>
  </div>
</template>