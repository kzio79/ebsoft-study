<script setup>
import { onMounted, ref } from 'vue'
import { useRoute } from 'vue-router'
import router from "@/router";
import apiService from "@/service/apiService";

const pw = ref('')
let viewBoardContent = ref(null)

const route = useRoute()

const boardNum = route.query.boardNum

//비밀번호 불러오기
const checkPw = async () => {
  try {

    const response = await apiService.getContent(boardNum)
    viewBoardContent.value = response.data.viewBoardContent

  } catch (error) {
    console.log('전송 에러: ', error)
  }
}

onMounted( async () => {
  await checkPw()
})

//불러온 비밀번호와 비교후 삭제
const deleteBoardContent = async () => {

  if (pw.value === viewBoardContent.value.pw) {
    alert('게시물이 삭제 되었습니다.')

    try {

      await apiService.postDelete(boardNum, pw)
      await router.push({ name: 'index' })

    } catch (error) {
      console.log('삭제 실패: ', error)
    }
  } else {
    alert('비밀번호가 일치하지 않습니다.')
  }
}
</script>

<template>
  <div style="display: flex; justify-content: center; align-items: center; margin: 20%">
    <article style="text-align: center; border: 3px solid #dddddd; width: 40%">
      <form @submit.prevent="deleteBoardContent">
        <table>
          <h3>비밀번호를 입력하세요</h3>
          <br />
          <input type="password" v-model="pw" placeholder="비밀번호를 입력하세요" required="required" />
          <input type="submit" value="삭제" />
        </table>
      </form>
    </article>
  </div>
</template>